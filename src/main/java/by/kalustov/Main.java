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
import java.util.concurrent.Flow;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        task1();
        task2();
        task3();
        task4();
        task5();
        task6();
        task7();
        task8();
        task9();
        task10();
        task11();
        task12();
        task13();
        task14();
        task15();
        task16();
    }


    private static void task1() throws IOException {
        System.out.println("task1");
        List<Animal> animals = Util.getAnimals();
                animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                        .sorted(Comparator.comparing(Animal::getAge))
                        .skip(14)
                        .limit(7)
                        .collect(Collectors.toList())
                        .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        System.out.println("task2");
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
        System.out.println("task3");
        List<Animal> animals = Util.getAnimals();
                animals.stream().filter(animal -> animal.getAge() >= 30 && animal.getOrigin().startsWith("A"))
                        .map(Animal::getOrigin)
                        .distinct()
                        .collect(Collectors.toList())
                        .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        System.out.println("task4");
        List<Animal> animals = Util.getAnimals();
                long count = animals.stream().filter(animal -> "Female".equals(animal.getGender()))
                        .count();
        System.out.println(count);
    }

    private static void task5() throws IOException {
        System.out.println("task5");
        List<Animal> animals = Util.getAnimals();
               boolean isAnyHungarian = animals.stream().filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                        .anyMatch(animal -> "Hungarian".equals(animal.getOrigin()));
        System.out.println(isAnyHungarian);
    }

    private static void task6() throws IOException {
        System.out.println("task6");
        List<Animal> animals = Util.getAnimals();
               boolean isAllMaleAndFemale = animals.stream().allMatch(animal -> "Female".equals(animal.getGender())
                       || "Male".equals(animal.getGender()));
        System.out.println(isAllMaleAndFemale);
    }

    private static void task7() throws IOException {
        System.out.println("task7");
        List<Animal> animals = Util.getAnimals();
               boolean isNoneOceania = animals.stream().noneMatch(animal -> "Oceania".equals(animal.getOrigin()));
        System.out.println(isNoneOceania);
    }

    private static void task8() throws IOException {
        System.out.println("task8");
        List<Animal> animals = Util.getAnimals();
               Optional<Integer> oldestAge = animals.stream().sorted(Comparator.comparing(Animal::getBread))
                        .limit(100)
                        .map(Animal::getAge)
                        .max(Integer::compareTo);
        System.out.println(oldestAge.get());

    }

    private static void task9() throws IOException {
        System.out.println("task9");
        List<Animal> animals = Util.getAnimals();
        Optional<char[]> minLength = animals.stream().flatMap(animal -> {
                    char[] bread = animal.getBread().toCharArray();
                    return Arrays.asList(bread).stream();
                }).min(Comparator.comparingInt(a -> a.length));
        System.out.println(minLength.get().length);
    }

    private static void task10() throws IOException {
        System.out.println("task10");
        List<Animal> animals = Util.getAnimals();
            Integer ageSum = animals.stream().map(Animal::getAge).reduce(0, Integer::sum);
            System.out.println(ageSum);
    }

    private static void task11() throws IOException {
        System.out.println("task11");
        List<Animal> animals = Util.getAnimals();
               OptionalDouble average = animals.stream().filter(animal -> "Indonesian".equals(animal.getOrigin()))
                       .mapToInt(Animal::getAge)
                       .average();
        System.out.println(average.getAsDouble());
    }

    private static void task12() throws IOException {
        System.out.println("task12");
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
        System.out.println("task13");
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
        System.out.println("task14");
        List<Car> cars = Util.getCars();

        BiFunction<Integer, Car, Integer> countryMassFunction = (sum, car) -> sum + car.getMass();

        Predicate<Car> tmPredicate = car -> "Jaguar".equals(car.getCarMake()) || "White".equals(car.getColor());
        Predicate<Car> uzPredicate = car -> car.getMass() <= 1500 || "BMW".equals(car.getCarMake())
                || "Lexus".equals(car.getCarMake()) || "Chrysler".equals(car.getCarMake())
                || "Toyota".equals(car.getCarMake());
        Predicate<Car> kzPredicate = car -> ("Black".equals(car.getColor()) && car.getMass() >= 4000)
                || "GMC".equals(car.getCarMake()) || "Dodge".equals(car.getCarMake());
        Predicate<Car> kgPredicate = car -> car.getReleaseYear() < 1982 || "Civic".equals(car.getCarModel())
                || "Cherokee".equals(car.getCarModel());
        Predicate<Car> ruPredicate = car -> !"Yellow".equals(car.getColor()) || !"Red".equals(car.getColor())
                || !"Green".equals(car.getColor()) || !"Blue".equals(car.getColor()) || car.getPrice() > 40000;
        Predicate<Car> mnPredicate = car -> car.getVin().contains("59");

        int tmMass = cars.stream().filter(tmPredicate).reduce(0, countryMassFunction, Integer::sum);

        List<Car> uzCars = cars.stream().dropWhile(tmPredicate).collect(Collectors.toList());
        int uzMass = uzCars.stream().filter(uzPredicate).reduce(0, countryMassFunction, Integer::sum);

        List<Car> kzCars = uzCars.stream().dropWhile(uzPredicate).collect(Collectors.toList());
        int kzMass = kzCars.stream().filter(kzPredicate).reduce(0, countryMassFunction, Integer::sum);

        List<Car> kgCars = kzCars.stream().dropWhile(kzPredicate).collect(Collectors.toList());
        int kgMass = kgCars.stream().filter(kgPredicate).reduce(0, countryMassFunction, Integer::sum);

        List<Car> ruCars = kgCars.stream().dropWhile(kgPredicate).collect(Collectors.toList());
        int ruMass = ruCars.stream().filter(ruPredicate).reduce(0, countryMassFunction, Integer::sum);

        List<Car> mnCars = ruCars.stream().dropWhile(ruPredicate).collect(Collectors.toList());
        int mnMass = mnCars.stream().filter(mnPredicate).reduce(0, countryMassFunction, Integer::sum);

        IntStream massStream = IntStream.of(tmMass, uzMass, kzMass, kgMass, ruMass, mnMass);
        AtomicReference<BigDecimal> profit = new AtomicReference<>(new BigDecimal("0"));
        massStream.forEach(mass -> {
            BigDecimal expense = BigDecimal.valueOf(mass*7.14/1000);
            profit.getAndUpdate(x -> x.add(expense));
            System.out.println("Mass = " + mass + ", expense = " + expense);
        });
        System.out.println("Profit = " + profit.get());

    }

    private static void task15() throws IOException {
        System.out.println("task15");
        List<Flower> flowers = Util.getFlowers();

        Comparator countryComparator = Comparator.comparing(Flower::getOrigin);
        Comparator priceComparator = Comparator.comparing(Flower::getPrice);
        Comparator waterConsumptionComparator = Comparator.comparing(Flower::getWaterConsumptionPerDay);
        Comparator nameComparator = Comparator.comparing(Flower::getCommonName);

        Predicate<Flower> nameFilter = flower -> {
            char letter = flower.getCommonName().charAt(0);
            return letter >= 67 && letter <= 83;
        };

        Predicate<Flower> shadeVaseFilter = flower -> flower.getFlowerVaseMaterial().stream()
                .anyMatch(material -> "Aluminum".equals(material) || "Glass".equals(material)
            || "Steel".equals(material)) && flower.isShadePreferred();


        Function<Flower, BigDecimal> waterConsumptionFunction = flower -> {
            BigDecimal waterConsumption = BigDecimal.valueOf(flower.getWaterConsumptionPerDay()/1000*1825*1.39);
            BigDecimal priceAndConsumption =  waterConsumption.add(BigDecimal.valueOf(flower.getPrice()));
            return waterConsumption;
        };

        Stream<BigDecimal> plantCareStream = flowers.stream().filter(nameFilter).filter(shadeVaseFilter).sorted(countryComparator.reversed()
                .thenComparing(priceComparator.reversed())
                .thenComparing(waterConsumptionComparator.reversed())
                .thenComparing(nameComparator.reversed()))
                .map(waterConsumptionFunction);

        BigDecimal plantCareSum = plantCareStream.reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("Total cost of care: " + plantCareSum);

    }

    public static void task16() throws IOException {
        System.out.println("task16");
        List<Person> people = Util.getPersons();
        // first task
        ToIntFunction<Person> averageAgeFunction = person -> Period.between(person.getDateOfBirth(), LocalDate.now()).getYears();

        Map<String, Double> ageByOccupation = people.stream()
                .collect(Collectors.groupingBy(Person::getOccupation,
                        Collectors.averagingInt(averageAgeFunction)));

        Predicate<Map.Entry<String, Double>> ageFilter = entry -> entry.getValue() >=20 && entry.getValue() <=25;

        ageByOccupation.entrySet().stream()
                .filter(ageFilter)
                .sorted(Map.Entry.<String, Double>comparingByKey().reversed())
                .forEach(entry -> System.out.println(entry.getKey() + " = " + String.format("%.0f", entry.getValue())));

        System.out.println("-----");

        // second task
        Function<Person, Integer> monthOfBirthdayFunction = person -> person.getDateOfBirth().getMonthValue();
        Map<Integer, List<String>> monthByOccupation = people.stream()
                .collect(Collectors.groupingBy(monthOfBirthdayFunction,
                        Collectors.mapping(Person::getOccupation, Collectors.toList())));

        monthByOccupation.values().stream().map(list -> {
            Map<String, Long> occupationCount = list.stream()
                .collect(Collectors.groupingBy(String::valueOf, Collectors.counting()));
           return Collections.max(occupationCount.entrySet(), Comparator.comparingLong(Map.Entry::getValue)).getKey();
        }).forEach(System.out::println);

    }
}