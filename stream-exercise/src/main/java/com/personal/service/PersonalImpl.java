package com.personal.service;

import com.personal.model.Gender;
import com.personal.model.Personal;
import com.personal.model.Pets;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    @Override
    public List<Personal> peopleHasPetType(List<Personal> List, Pets petType) {
        return List.stream()
                .filter(p -> p.getPet().contains(petType))
                .collect(Collectors.toList());
    }

    @Override
    public Gender mostFavoriteGenderHasPetType(List<Personal> List, Pets petType) {
        Map<Gender, Long> genderLongMap = countPeopleByGender(List);
        Long numberOfMale = genderLongMap.get(Gender.MALE);
        Long numberOfFemale = genderLongMap.get(Gender.FEMALE);

        List<Personal> peopleHasCat = peopleHasPetType(List, petType);
        Map<Gender, Long> mapByGenderWhoHasPet = countPeopleByGender(peopleHasCat);

        Long numberOfMaleWhoHasPet = mapByGenderWhoHasPet.get(Gender.MALE);
        Long numberOfFemaleWhoHasPet = mapByGenderWhoHasPet.get(Gender.FEMALE);

        double maleRatio = (double) numberOfMaleWhoHasPet / numberOfMale;
        double femaleRatio = (double) numberOfFemaleWhoHasPet / numberOfFemale;

        return maleRatio > femaleRatio?Gender.MALE : Gender.FEMALE;
    }

    @Override
    public Boolean PeopleWhoDoesNotHasPet(List<Personal> personalList) {
        return personalList.stream()
                .anyMatch(p -> p.getPet().isEmpty());

    }

    @Override
    public Personal peopleWhoSmallerThanAge(List<Personal> personalList) {
        return personalList.stream()
                .min((a,b) -> a.getAge() - b.getAge())
                .get();
    }

    @Override
    public Set<Pets> getAllPetType(List<Personal> personalList) {
        return personalList.stream()
                .flatMap(p -> p.getPet().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Map<Integer, List<Personal>> groupPeopleByAge(List<Personal> personalList) {
        return personalList.stream()
                .collect(Collectors.groupingBy(Personal::getAge));
    }

    @Override
    public Map<Boolean, List<Personal>> groupPeopleByAge(List<Personal> personalList, Integer age) {
        return personalList.stream()
                .collect(Collectors.groupingBy(p -> p.getAge() >= age));
    }


    @Override
    public Map<Pets, List<String>> groupPeopleByPetType(List<Personal> personalList) {
        return personalList.stream()
                .flatMap(p -> p.getPet().stream()
                        .map(pet -> new AbstractMap.SimpleEntry<>(pet, p.getName())))
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())));

    }

    // this Option 2 groupPeopleByPetType
    public Map<Pets, List<String>> groupPeopleByPetType2(List<Personal> personalList) {
        return personalList.stream()
                .flatMap(p -> p.getPet().stream()
                        .map(pet -> new PersonPet(p.getName(), pet)))
                .collect(Collectors.groupingBy(PersonPet::getPet,
                        Collectors.mapping(pp -> pp.name, Collectors.toList())));

    }

    @Override
    public List<Personal> filter(List<Personal> personalList) {
        return personalList.stream()
                .filter(personal -> personal.getPet().contains(Pets.CAT))
                .filter(personal -> !personal.getPet().contains(Pets.DOG))
                .filter(personal -> personal.getGender() == Gender.FEMALE)
                .filter(personal -> personal.getAge() > 19 && personal.getAge() < 21)
                .toList();
    }

    private static class PersonPet{
        String name;
        Pets pet;

        public PersonPet(String name, Pets pet) {
            this.name = name;
            this.pet = pet;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Pets getPet() {
            return pet;
        }

        public void setPet(Pets pet) {
            this.pet = pet;
        }
    }

}
