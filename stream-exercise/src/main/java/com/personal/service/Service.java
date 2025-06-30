package com.personal.service;

import com.personal.model.Gender;
import com.personal.model.Personal;
import com.personal.model.Pets;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface Service {

    Map<Gender, Long> countPeopleByGender(List<Personal> personalList);
    List<Personal> findPeopleHadPetsMoreThanOne(List<Personal> personalList, Integer numberOfPets);
    List<Personal> peopleHasPetType(List<Personal> List, Pets petType);
    Gender mostFavoriteGenderHasPetType(List<Personal> personalList, Pets petType);
    Boolean PeopleWhoDoesNotHasPet(List<Personal> personalList);
    Personal peopleWhoSmallerThanAge(List<Personal> personalList);
    Set<Pets> getAllPetType(List<Personal> personalList);
    Map<Integer, List<Personal>> groupPeopleByAge(List<Personal> personalList);
    Map<Boolean, List<Personal>> groupPeopleByAge(List<Personal> personalList, Integer age);
    Map<Pets, List<String>> groupPeopleByPetType(List<Personal> personalList );
    List<Personal> filter(List<Personal> personalList);


}
