package com.personal.service;

import com.personal.model.Gender;
import com.personal.model.Personal;

import java.util.List;
import java.util.Map;

public interface Service {

    Map<Gender, Long> countPeopleByGender(List<Personal> personalList);
    List<Personal> findPeopleHadPetsMoreThanOne(List<Personal> personalList, Integer numberOfPets);

}
