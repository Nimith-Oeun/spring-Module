package com.personal;

import com.personal.model.Gender;
import com.personal.model.Personal;
import com.personal.model.Pets;
import com.personal.service.PersonalImpl;
import com.personal.service.Service;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
//@SpringBootApplication
public class StreamExerciseApplication {

    private final Service service = new PersonalImpl();
    private final List<Personal> personalList = List.of(
            new Personal("Alice",20, Gender.FEMALE, List.of(Pets.CAT, Pets.DOG)),
            new Personal("dara",54,Gender.MALE, List.of(Pets.DOG)),
            new Personal("rotha",54, Gender.MALE, List.of(Pets.CAT, Pets.DOG , Pets.FISH)),
            new Personal("chana",15, Gender.FEMALE, List.of()),
            new Personal("kaka",20, Gender.FEMALE, List.of(Pets.CAT)),
            new Personal("rara",43, Gender.MALE, List.of(Pets.BIRD)),
            new Personal("tata",19, Gender.FEMALE, List.of(Pets.BIRD, Pets.DOG)),
            new Personal("jeck",19, Gender.MALE, List.of( Pets.DOG))
    );


    public static void main(String[] args) {
//        SpringApplication.run(StreamExerciseApplication.class, args);
        StreamExerciseApplication application = new StreamExerciseApplication();
        application.showNumberOfPeopleByGender();
        application.showPeopleHadPetsMoreThanOne();
        application.peopleHasPetType();
        application.genderFavoritPet();
        application.whoDoesNotHasPet();
        application.youngesPeople();
        application.getAllpet();
        application.groupPeopleByAge();
        application.groupPeopleByAge18Up();
        application.groupPeopleBypet();
        application.filter();
    }

//    @PostConstruct // This method will be called after the application context is loaded
    public void showNumberOfPeopleByGender() {
        Map<Gender, Long> genderLongMap = service.countPeopleByGender(personalList);
        System.out.println(genderLongMap);
    }

    public void showPeopleHadPetsMoreThanOne() {
        System.out.println("=".repeat(10)+"នកណាខ្លះចិញ្ចឹមសត្វលើសពី១ប្រភេទ"+"=".repeat(10));
        List<Personal> person = service.findPeopleHadPetsMoreThanOne(personalList, 1);
        person.forEach(p -> System.out.println(p.getName()));
    }

    public void peopleHasPetType() {
        System.out.println("=".repeat(10)+"អ្នកណាខ្លះចិញ្ចឹមឆ្មា"+"=".repeat(10));
        List<Personal> person = service.peopleHasPetType(personalList, Pets.CAT);
        person.forEach(p -> System.out.println(p.getName()));
    }

    public void genderFavoritPet() {
        System.out.println("=".repeat(10)+"ប្រុសឬស្រីដែលចូលចិត្តចិញ្ចឹមឆ្មាជាងគេ"+"=".repeat(10));
        Gender gender = service.mostFavoriteGenderHasPetType(personalList, Pets.CAT);
        System.out.println(gender);
    }

    public void whoDoesNotHasPet() {
        System.out.println("=".repeat(10)+"មានអ្នកដែលអត់ចិញ្ចឹមសត្វដែរឬទេ"+"=".repeat(10));
        Boolean whoDoesNotHasPet = service.PeopleWhoDoesNotHasPet(personalList);
        System.out.println(whoDoesNotHasPet);
    }

    public void youngesPeople() {
        System.out.println("=".repeat(10)+"មនុស្សដែលមានអាយុតិចជាងគេ"+"=".repeat(10));
        Personal thanAge = service.peopleWhoSmallerThanAge(personalList);
        System.out.println(thanAge);
    }

    public void getAllpet() {
        System.out.println("=".repeat(10)+"ដាក់មនុស្សក្នុងភូមិជាក្រុមៗតាមអាយុ"+"=".repeat(10));
        Set<Pets> allPetType = service.getAllPetType(personalList);
        allPetType.forEach(System.out::println);
    }

    public void groupPeopleByAge() {
        System.out.println("=".repeat(10)+"ភូមិនេះមានសត្វចិញ្ចឹមប៉ុន្មានប្រភេទ"+"=".repeat(10));
        Map<Integer, List<Personal>> integerListMap = service.groupPeopleByAge(personalList);
        integerListMap.forEach((age, people) -> {
            System.out.println("Age: " + age);
            people.forEach(p -> System.out.println(" - " + p.getName()));
        });
    }

    public void groupPeopleByAge18Up() {
        System.out.println("=".repeat(10)+"អ្នកណាខ្លះគ្រប់អាយុបោះឆ្នោត អ្នកណាខ្លះមិនទាន់គ្រប់អាយុបោះឆ្នោត"+"=".repeat(10));
        Map<Boolean, List<Personal>> booleanListMap = service.groupPeopleByAge(personalList, 18);
        booleanListMap.forEach((isEligible, people) -> {
            String eligibility = isEligible ? "Eligible to vote" : "Not eligible to vote";
            System.out.println(eligibility + ":");
            people.forEach(p -> System.out.println(" - " + p.getName()));
        });
    }

    public void groupPeopleBypet() {
        System.out.println("=".repeat(10)+"បង្ហាញឈ្មោះអ្នកដែលចិញ្ចឹមសត្វ តាមប្រភេទសត្វនីមួយ"+"=".repeat(10));
        Map<Pets, List<String>> petsListMap = service.groupPeopleByPetType(personalList);
        petsListMap.forEach((pet, names) -> {
            System.out.println("Pet: " + pet);
            names.forEach(name -> System.out.println(" - " + name));
        });
    }

    public void filter() {
        System.out.println("=".repeat(10)+"បង្ហាញឈ្មោះនិងអាយុរបស់អ្នកដែលចិញ្ចឹមឆ្មាតែអត់ចិញ្ចឹមសុនក ភេទស្រី ដែលមានអាយុចន្លោះពី១៩ ទៅ ២១"+"=".repeat(10));
        List<Personal> filter = service.filter(personalList);
        filter.forEach(System.out::println);
    }





}