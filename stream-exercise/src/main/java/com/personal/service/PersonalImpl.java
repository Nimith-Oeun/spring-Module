package com.personal.service;

import com.personal.model.Gender;
import com.personal.model.Personal;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Locale.filter;

public class PersonalImpl implements Service{

    @Override
    public Map<Gender, Long> countPeopleByGender(List<Personal> personalList) {
        return personalList.stream()
                .collect(Collectors.groupingBy(Personal::getGender, Collectors.counting()));
    }

    @Override
    public List<Personal> findPeopleHadPetsMoreThanOne(List<Personal> personalList, Integer numberOfPets) {
        return personalList.stream()
                .filter(p -> p.getPet().size() > numberOfPets)
                .collect(Collectors.toList());
    }
}
