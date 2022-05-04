package com.company;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Jack", "Connor", "Harry", "George", "Samuel", "John");
        List<String> families = Arrays.asList("Evans", "Young", "Harris", "Wilson", "Davies", "Adamson", "Brown");
        Collection<Person> persons = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            persons.add(new Person(
                    names.get(new Random().nextInt(names.size())),
                    families.get(new Random().nextInt(families.size())),
                    new Random().nextInt(100),
                    Sex.values()[new Random().nextInt(Sex.values().length)],
                    Education.values()[new Random().nextInt(Education.values().length)])
            );
        }
        long ageCount = persons.stream() //Найти количество несовершеннолетних (т.е. людей младше 18 лет).
                .filter(age -> age.getAge() < 18)
                .count();
        System.out.println(ageCount);
        List<String> conscript = persons.stream() //Получить список фамилий призывников (т.е. мужчин от 18 и до 27 лет).
                .filter(age -> age.getAge() > 18)
                .filter(age -> age.getAge() < 27)
                .filter(sex -> sex.getSex() == Sex.MAN)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println(conscript);
        List<String> workable = persons.stream() //Получить отсортированный по фамилии список потенциально работоспособных людей с высшим образованием в выборке
                // (т.е. людей с высшим образованием от 18 до 60 лет для женщин и до 65 лет для мужчин).
                .filter(education -> education.getEducation() == Education.HIGHER)
                .filter(age -> age.getAge() > 18)
                .filter(sex -> sex.getSex() == Sex.MAN || sex.getAge() < 60)
                .filter(sex -> sex.getSex() == Sex.WOMAN || sex.getAge() < 65)
                .map(Person::getFamily)
                .collect(Collectors.toList());
        System.out.println(workable);
    }
}
