# 🔐 KeyCloak-Oauth2 Client + Resource Server Intergrate
## 👉 Step-By-Step
```
┌───────────────────────────────┐
│          User Browser         │
│  (Login via GitHub button)    │
└──────────────┬────────────────┘
               │
               ▼
        [1] Redirect to
        GitHub Login Page
               │
               ▼
┌───────────────────────────────┐
│          GitHub OAuth         │
│  - Authenticates user         │
│  - Returns user info          │
└──────────────┬────────────────┘
               │
               ▼
     [2] Redirect back to
     Keycloak (Identity Broker)
               │
               ▼
┌───────────────────────────────┐
│           Keycloak            │
│ Realm: NIMITH                 │
│ Client: github-nimith-cc      │
│ Identity Provider: GitHub     │
│                               │
│  [3] Check if user exists     │
│   ├─ If not, create user      │
│   │   (federated user)        │
│   │                           │
│   ├─ If Default Role set      │
│   │   → Assign automatically  │
│   │                           │
│   ├─ If IDP Role Mapper set   │
│   │   → Inject role (e.g user)│
│   │                           │
│   └─ If manual mapping        │
│       → Admin assigns later   │
└──────────────┬────────────────┘
               │
               ▼
     [4] Keycloak issues tokens
     - access_token (JWT)
     - id_token
     - refresh_token
               │
               ▼
┌───────────────────────────────┐
│       Spring Boot App         │
│  (OAuth2 Resource Server)     │
│                               │
│ - Validates JWT (issuer-uri)  │
│ - Extracts roles:             │
│   resource_access.github-nimith-cc.roles │
│                               │
│ - Applies Spring Security     │
│   rules (ROLE_USER, etc.)     │
└───────────────────────────────┘

```
  ## 1) Create a GitHub OAuth App
  <img width="680" height="255" alt="image" src="https://github.com/user-attachments/assets/9a6d7c22-6a04-472b-a550-bf37acb39b33" />
  
  ## 2) Configure GitHub as an Identity Provider in Keycloak
  <img width="787" height="307" alt="image" src="https://github.com/user-attachments/assets/8750f61d-363e-403c-930e-06cd52da0bcb" />
  
  ## 3) Create a Client in Keycloak (for your Spring App)
  <img width="770" height="272" alt="image" src="https://github.com/user-attachments/assets/b3bb6f5b-3068-4ae2-b449-29a2b8956efe" />
  
  ## 4) Map GitHub attributes → Keycloak token claims / roles
  👉 Set default roles to new user that authorized via Platform
  - Go to Keycloak → Clients → github-nimith-cc → Roles → Create roles (e.g. user).
  - Go to Realm Settings → User Registration → Default Roles.
  - Add your client role (e.g. github-nimith-cc.user) to the default realm roles.
    
  ✍ New users (including GitHub federated ones) will automatically get that role at first login.

## 5) Spring Boot: application.yml (use Keycloak as issuer)
```
server:
  port: 9090

spring:
  security:
    oauth2:
      client:
        registration:
          keycloak:
            client-id: <KEYCLOAK_CLIENT_ID>
            client-secret: <KEYCLOAK_CLIENT_SECRET>
            provider: keycloak
            scope:
              - openid
              - profile
              - email
        provider:
          keycloak:
            issuer-uri: <KEYCLOAK_HOST>/realms/<REALM>
      resourceserver:
        jwt:
          issuer-uri: <KEYCLOAK_HOST>/realms/<REALM>

jwt:
  auth:
    converter:
      resource-id: <KEYCLOAK_CLIENT_ID>
      principle-attribute: preferred_username
```

## 6) Spring Security config (Java)
```
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtConverter jwtConverter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated()
                )
                .oauth2Login(Customizer.withDefaults())
                .oauth2ResourceServer((oauth2) -> oauth2.jwt(
                        jwt -> jwt.jwtAuthenticationConverter(jwtConverter)
                ));

        return http.build();
    }
}
```

## 7) Mapping Keycloak roles/scopes → Spring Authorities
```
@Service
public class JwtConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter =
            new JwtGrantedAuthoritiesConverter();

    @Value("${jwt.auth.converter.principle-attribute}")
    private String principleAttribute;
    @Value("${jwt.auth.converter.resource-id}")
    private String resourceId;

    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {

        Collection<GrantedAuthority> authorities = Stream.concat(
                jwtGrantedAuthoritiesConverter.convert(jwt).stream(),
                extractResourceRoles(jwt).stream()
        ).collect(Collectors.toSet());

        return new JwtAuthenticationToken(
                jwt,
                authorities,
                getPrincipleClaimName(jwt)
        );
    }

    //
    private String getPrincipleClaimName(Jwt jwt) {
        String claimName = JwtClaimNames.SUB;
        if (principleAttribute != null) {
            claimName = principleAttribute;
        }
        return jwt.getClaim(claimName);
    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        Map<String, Object> resourceAccess;
        Map<String, Object> resource;
        Collection<String> resourceRoles;
        if (jwt.getClaim("resource_access") == null) {
            return Set.of();
        }
        resourceAccess = jwt.getClaim("resource_access");

        if (resourceAccess.get(resourceId) == null) {
            return Set.of();
        }
        resource = (Map<String, Object>) resourceAccess.get(resourceId);

        resourceRoles = (Collection<String>) resource.get("roles");

        return resourceRoles
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());
    }

}
```

## 8) Example Controller

```
@RestController
@RequestMapping
public class ControllerEanpoint {

    @GetMapping("/")
    public String publicEndpoint1() {
        return "Home page";
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "public Page";
    }

    // For Debuge Payload jwt
    @GetMapping("/api/secure")
    public Map<String, Object> secure(@AuthenticationPrincipal Jwt jwt) {
        if (jwt == null) {
            return Map.of("error", "No JWT found — use Authorization: Bearer <token>");
        }
        return Map.of(
                "loginType", "JWT Access Token" + jwt.getTokenValue(),
                "subject", jwt.getSubject(),
                "email", jwt.getClaim("email"),
                "name", jwt.getClaim("preferred_username")
        );
    }


    @GetMapping("/admin")
    @PreAuthorize("hasRole('role_admin')")
    public String admin() {
        return "admin area";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('role_user')")
    public String user() {

        return "user area";
    }
```
## 9) Testing locally
- Start Keycloak (docker or server) and create the realm/client/IDP.
- Start your Spring Boot app.
- Visit http://localhost:8080/profile — you should be redirected to Keycloak → GitHub login → back to app with JWT.
- Inspect JWT with jwt.io or from Spring’s @AuthenticationPrincipal Jwt.
For API clients (e.g., Postman), obtain an access token from Keycloak (client credentials or the token endpoint after login) and call protected endpoints with:
```
Authorization: Bearer <KEYCLOAK_JWT>
```

 # Testing Via Postman (To get Access Token)
 # 👉 Step-By-Step
  - Open Postman → go to Authorization tab
  - Choose: OAuth 2.0
  - Scrol down and inut fiel
  <img width="609" height="534" alt="image" src="https://github.com/user-attachments/assets/a7ee5a87-dc98-4f6f-8bc0-9ebaf9642ace" />
  
  - Click Get New Access Token
    
  ✍ Fiel Callback URL(Postman) must match what’s in Keycloak → Valid redirect URIs, So you must add more Valid redirect URIs in client(keycloak admin)
```
https://oauth.pstmn.io/v1/callback
```
<img width="1051" height="137" alt="image" src="https://github.com/user-attachments/assets/aacb0da6-61a1-4938-8d1e-cc06b4e5f4b3" />

# ✍️Bunnus
```
[ User ]
   ↓
   → Login via GitHub / Facebook / Username-password
   ↓
[ Keycloak ]
   ↓ issues JWT
[ Spring Boot App (Resource Server) ]
   ↓ verifies JWT
[ Application APIs ]

```
