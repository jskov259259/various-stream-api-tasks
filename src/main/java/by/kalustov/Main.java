package by.kalustov;

import by.kalustov.model.Animal;
import by.kalustov.model.Car;
import by.kalustov.model.Flower;
import by.kalustov.model.House;
import by.kalustov.model.Person;
import by.kalustov.util.Util;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
        task14();
        task15();
    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
                animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                        .sorted(Comparator.comparing(Animal::getAge))
                        .skip(14)
                        .limit(7)
                        .collect(Collectors.toList())
                        .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
                animals.stream().filter(animal -> animal.getOrigin().equals("Japanese"))
                        .map(animal -> {
                            String bread = animal.getBread();
                            if ("Female".equals(animal.getGender())) animal.setBread(bread.toUpperCase());
                            return animal;
                        })
                        .collect(Collectors.toList())
                        .forEach(System.out::println);

    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
                animals.stream().filter(animal -> animal.getAge() >= 30 && animal.getOrigin().startsWith("A"))
                        .map(Animal::getOrigin)
                        .distinct()
                        .collect(Collectors.toList())
                        .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
                long count = animals.stream().filter(animal -> "Female".equals(animal.getGender()))
                        .count();
        System.out.println(count);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
               boolean isAnyHungarian = animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                        .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));
        System.out.println(isAnyHungarian);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
               boolean isAllMaleAndFemale = animals.stream().allMatch(animal -> "Female".equals(animal.getGender())
                       || "Male".equals(animal.getGender()));
        System.out.println(isAllMaleAndFemale);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
               boolean isNoneOceania = animals.stream().noneMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(isNoneOceania);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
               Optional<Integer> oldestAge = animals.stream().sorted(Comparator.comparing(Animal::getBread))
                        .limit(100)
                        .map(Animal::getAge)
                        .max(Integer::compareTo);
        System.out.println(oldestAge.get());

    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        Optional<char[]> minLength = animals.stream().flatMap(animal -> {
                    char[] bread = animal.getBread().toCharArray();
                    return Arrays.asList(bread).stream();
                }).min(Comparator.comparingInt(a -> a.length));
        System.out.println(minLength.get().length);
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
            Integer ageSum = animals.stream().map(Animal::getAge).reduce(0, Integer::sum);
            System.out.println(ageSum);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
               OptionalDouble average = animals.stream().filter(animal -> "Indonesian".equals(animal.getOrigin()))
                       .mapToInt(Animal::getAge)
                       .average();
        System.out.println(average.getAsDouble());
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream().filter(person -> {
            Integer age = Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
            return "Male".equals(person.getGender()) && (age >= 18 && age <= 27);
        })
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200)
                .forEach(System.out::println);

    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();
        List<Person> patients = new ArrayList<>();
        List<Person> youngsAndOlds = new ArrayList<>();
        List<Person> others = new ArrayList<>();
        houses.stream().forEach(house -> {
                    List<Person> persons = house.getPersonList();
                    persons.stream().forEach(person -> {
                        if ("Hospital".equals(house.getBuildingType())) {
                            patients.add(person);
                            return;
                        }
                        Integer age = Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
                        if (age < 18) {
                            youngsAndOlds.add(person);
                            return;
                        } else {
                            if (age >= 58 && "Female".equals(person.getGender())) {
                                youngsAndOlds.add(person);
                                return;
                            } else {
                                if (age >= 63 && "Male".equals(person.getGender())) {
                                    youngsAndOlds.add(person);
                                    return;
                                } else {
                                    others.add(person);
                                }
                            }
                        }
                    });
                });

            Stream.concat(Stream.concat(patients.stream(), youngsAndOlds.stream()), others.stream())
                    .limit(500)
                    .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();
        AtomicReference<BigDecimal> revenue = new AtomicReference<>(new BigDecimal("0"));

        int tm_mass = cars.stream().filter(car -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor()))
                .peek(System.out::println)
                       .reduce(0, (sum, car) -> {
                    BigDecimal expense = BigDecimal.valueOf(car.getMass()*7.14/1000);
                    revenue.getAndUpdate(x -> x.add(expense));
                    return sum + car.getMass();
                }, (sum, car) -> sum + car);
        Stream<Car> uz_stream = cars.stream().filter(car -> car.getMass() <= 1500 || "BMW".equals(car.getCarModel())
                        || "Lexus".equals(car.getCarModel()) || "Chrysler".equals(car.getCarModel())
                        || "Toyota".equals(car.getCarModel()));
        Stream<Car> kz_stream = cars.stream().filter(car -> ("Black".equals(car.getColor()) && car.getMass() >= 4000)
                        || "GMC".equals(car.getCarModel()) || "Dodge".equals(car.getCarModel()));
        Stream<Car> kg_stream = cars.stream().filter(car -> car.getReleaseYear() < 1982 || "Civic".equals(car.getCarModel())
                        || "Cherokee".equals(car.getCarModel()));
        Stream<Car> ru_stream = cars.stream().filter(car -> !"Yellow".equals(car.getColor()) || !"Red".equals(car.getColor())
                || !"Green".equals(car.getColor()) || !"Blue".equals(car.getColor()) || car.getPrice() > 40000);
        Stream<Car> mn_stream = cars.stream().filter(car -> car.getVin().contains("59"));

        System.out.println(tm_mass);
        System.out.println(revenue.get());

    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}