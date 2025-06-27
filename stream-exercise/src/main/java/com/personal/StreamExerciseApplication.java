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

@Slf4j
//@SpringBootApplication
public class StreamExerciseApplication {

    private final Service service = new PersonalImpl();
    private final List<Personal> personalList = List.of(
            new Personal("Alice",20, Gender.FEMALE, List.of(Pets.CAT, Pets.DOG)),
            new Personal("dara",21,Gender.MALE, List.of(Pets.DOG)),
            new Personal("rotha",54, Gender.MALE, List.of(Pets.CAT, Pets.DOG , Pets.FISH)),
            new Personal("chana",15, Gender.FEMALE, List.of()),
            new Personal("kaka",34, Gender.FEMALE, List.of(Pets.CAT, Pets.DOG)),
            new Personal("rara",43, Gender.MALE, List.of(Pets.BIRD)),
            new Personal("tata",19, Gender.FEMALE, List.of(Pets.CAT, Pets.DOG)),
            new Personal("jeck",29, Gender.MALE, List.of( Pets.DOG))
    );


    public static void main(String[] args) {
//        SpringApplication.run(StreamExerciseApplication.class, args);
        StreamExerciseApplication application = new StreamExerciseApplication();
        application.showNumberOfPeopleByGender();
        application.showPeopleHadPetsMoreThanOne();
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



}