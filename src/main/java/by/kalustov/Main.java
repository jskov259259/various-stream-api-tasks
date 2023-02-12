package by.kalustov;

import by.kalustov.model.Animal;
import by.kalustov.model.Car;
import by.kalustov.model.Flower;
import by.kalustov.model.House;
import by.kalustov.model.Person;
import by.kalustov.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
                            if (animal.getGender().equals("Female")) animal.setBread(bread.toUpperCase());
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
                long count = animals.stream().filter(animal -> animal.getGender().equals("Female"))
                        .count();
        System.out.println(count);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
               boolean isAnyHungarian = animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                        .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));
        System.out.println(isAnyHungarian);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
               boolean isAllMaleAndFemale = animals.stream().allMatch(animal -> animal.getGender().equals("Female") || animal.getGender().equals("Male"));
        System.out.println(isAllMaleAndFemale);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
               boolean isNoneOceania = animals.stream().noneMatch(animal -> animal.getOrigin().equals("Oceania"));
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
               OptionalDouble average = animals.stream().filter(animal -> animal.getOrigin().equals("Indonesian"))
                       .mapToInt(Animal::getAge)
                       .average();
        System.out.println(average.getAsDouble());
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream().filter(person -> {
            Integer age = Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
            return person.getGender().equals("Male") && (age >= 18 && age <= 27);
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
                        if (house.getBuildingType().equals("Hospital")) {
                            patients.add(person);
                            return;
                        }
                        Integer age = Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();
                        if (age < 18) {
                            youngsAndOlds.add(person);
                            return;
                        } else {
                            if (age >= 58 && person.getGender().equals("Female")) {
                                youngsAndOlds.add(person);
                                return;
                            } else {
                                if (age >= 63 && person.getGender().equals("Male")) {
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

        Comparator<Car> tm_comparator = (car1, car2) -> {
            if (car1.getCarMake().equals("Jaguar") || car1.getColor().equals("White")) {
                return 1;
            }
            if (car2.getCarMake().equals("Jaguar") || car2.getColor().equals("White")) {
                return -1;
            }
            return 0;
        };
        Comparator<Car> uz_comparator = (car1, car2) -> {
           if (car1.getMass() <= 1500 || car1.getCarModel().equals("BMW") || car1.getCarModel().equals("Lexus") || car1.getCarModel().equals("Chrysler")
                    || car1.getCarModel().equals("Toyota")) {
               return 1;
           }
            if (car2.getMass() <= 1500 || car2.getCarModel().equals("BMW") || car2.getCarModel().equals("Lexus") || car2.getCarModel().equals("Chrysler")
                    || car2.getCarModel().equals("Toyota")) {
                return -1;
            }
            return 0;
            };

        Comparator<Car> kz_comparator = (car1, car2) -> {
            if ((car1.getColor().equals("Black") && car1.getMass() >= 4000) || car1.getCarModel().equals("GMC") || car1.getCarModel().equals("Dodge")) {
                return 1;
            }
            if ((car2.getColor().equals("Black") && car2.getMass() >= 4000) || car2.getCarModel().equals("GMC") || car2.getCarModel().equals("Dodge")) {
                return -1;
            }
            return 0;
        };

        Comparator<Car> kg_comparator = (car1, car2) -> {
            if (car1.getReleaseYear() < 1982 || car1.getCarModel().equals("Civic") || car1.getCarModel().equals("Cherokee")) {
                return 1;
            }
            if (car2.getReleaseYear() < 1982 || car2.getCarModel().equals("Civic") || car2.getCarModel().equals("Cherokee")) {
                return -1;
            }
            return 0;
        };

        Comparator<Car> ru_comparator = (car1, car2) -> {
            if (!car1.getColor().equals("Yellow") || !car1.getColor().equals("Red") || !car1.getColor().equals("Green") || !car1.getColor().equals("Blue")
                    || car1.getPrice() > 40000) {
                return 1;
            }
            if (!car2.getColor().equals("Yellow") || !car2.getColor().equals("Red") || !car2.getColor().equals("Green") || !car2.getColor().equals("Blue")
                    || car2.getPrice() > 40000) {
                return -1;
            }
            return 0;
        };

        Comparator<Car> mn_comparator = (car1, car2) -> {
            if (car1.getVin().contains("59")) {
                return 1;
            }
            if (car2.getVin().contains("59")) {
                return -1;
            }
            return 0;
        };

        cars.stream().sorted(tm_comparator.thenComparing(uz_comparator).thenComparing(kz_comparator).thenComparing(kg_comparator)
        .thenComparing(ru_comparator).thenComparing(mn_comparator)).forEach(System.out::println);

    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();
        //        Продолжить...
    }
}