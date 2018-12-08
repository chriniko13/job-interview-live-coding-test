package com.chriniko.jobinterview.livecoding.test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

class SecondExercise {

    private static final boolean PRINT_LIST_PARAMETERIZED_TYPE = false;

    static class Person {
        String id;
        String name;
        int age;
        Float xp;
        Salary salary;
        Person[] coworkers;
        List<Integer> ticketsIds;
        Set<Integer> coffeeIds;
        List<Person> coworkersAsList;
    }

    static class Salary {
        double current;
        Double[] previousSalaries;
    }

    /*
        Note: write a program which will print all the properties (contents) of the provided object.
     */
    void run() {

        // Note: create data in order to play...
        Salary salary = new Salary();
        salary.current = 2090D;
        salary.previousSalaries = new Double[]{1400D, 1800D};

        Person person = new Person();
        person.id = "123";
        person.name = "john doe";
        person.age = 26;
        person.xp = 4.5F;
        person.salary = salary;
        List<Integer> ticketIds = new ArrayList<>();
        ticketIds.add(1);
        ticketIds.add(2);
        ticketIds.add(3);
        person.ticketsIds = ticketIds;
        Set<Integer> coffeeIds = new HashSet<>();
        coffeeIds.add(4);
        coffeeIds.add(5);
        person.coffeeIds = coffeeIds;


        Salary salary2 = new Salary();
        salary2.current = 3245;
        salary2.previousSalaries = new Double[]{1800D, 2200D, 2700D, 3000D};

        Person person2 = new Person();
        person2.id = "124";
        person2.name = "foo bar";
        person2.age = 32;
        person2.xp = 6.5F;
        person2.salary = salary2;

        person.coworkers = new Person[]{person2};
        person.coworkersAsList = Collections.singletonList(person2);


        /*
            Note: with the following statement we have cyclic reference and we will get a java.lang.StackOverflowError...
                  if we did not use the solution of Set.
         */
        person2.coworkers = new Person[]{person};

        // Note: run it...
        try {
            final StringBuilder sb = new StringBuilder();
            print(person, sb, new HashSet<>());

            System.out.println(sb.toString());
        } catch (Exception error) {
            error.printStackTrace(System.err);
        }
    }

    private void print(Object object,
                       StringBuilder sb,
                       Set<Object> objectsVisited /*Note: in order to eliminate cyclic references*/) throws IllegalAccessException {

        if (!objectsVisited.add(object)) {
            return;
        }

        sb.append("\n--- Visited Type: ").append(object.getClass().getSimpleName()).append(" ---\n");

        Class<?> objectClass = object.getClass();

        if (objectClass.isPrimitive() || primitiveWrappers.contains(objectClass)) {
            printFieldInfo(object, sb, objectClass);
        } else {

            Field[] declaredFields = objectClass.getDeclaredFields();

            for (Field declaredField : declaredFields) {

                sb.append("\n--- Visited Declared Field: ").append(declaredField.getName()).append(" ---\n");

                Class<?> declaredFieldType = declaredField.getType();

                if (iterableStructures.contains(declaredFieldType)) {

                    if (PRINT_LIST_PARAMETERIZED_TYPE) {
                        ParameterizedType listParameterizedType = (ParameterizedType) declaredField.getGenericType();
                        Class<?> listActualType = (Class<?>) listParameterizedType.getActualTypeArguments()[0];
                        System.out.println("listActualType: " + listActualType);
                    }

                    Collection<?> collection = (Collection<?>) declaredField.get(object);
                    if (collection != null) {
                        for (Object elem : collection) {
                            print(elem, sb, objectsVisited);
                        }
                    }

                } else if (!declaredFieldType.isArray()) {

                    if (declaredFieldType.isPrimitive() || primitiveWrappers.contains(declaredFieldType)) {
                        printFieldInfo(declaredField.get(object), sb, declaredField);

                    } else {
                        if (declaredFieldType.equals(String.class)) {
                            printFieldInfo(declaredField.get(object), sb, declaredField);

                        } else {
                            print(declaredField.get(object), sb, objectsVisited);
                        }
                    }
                } else {
                    Object array = declaredField.get(object);

                    if (array != null) {
                        int arrayLength = Array.getLength(array);

                        for (int i = 0; i < arrayLength; i++) {

                            Object arrayObject = Array.get(array, i);

                            if (arrayObject.getClass().isPrimitive() || primitiveWrappers.contains(arrayObject.getClass())) {

                                printFieldInfo(arrayObject, sb, declaredField);

                            } else {
                                print(arrayObject, sb, objectsVisited);
                            }
                        }
                    } else {
                        printFieldInfo(null, sb, declaredField);
                    }
                }
            }
        }
    }

    private final Set<Class<?>> iterableStructures = new HashSet<>();

    {
        iterableStructures.add(List.class);
        iterableStructures.add(Set.class);
    }

    private final Set<Class<?>> primitiveWrappers = new HashSet<>();

    {
        primitiveWrappers.add(Boolean.class);
        primitiveWrappers.add(Byte.class);
        primitiveWrappers.add(Character.class);
        primitiveWrappers.add(Double.class);
        primitiveWrappers.add(Float.class);
        primitiveWrappers.add(Integer.class);
        primitiveWrappers.add(Long.class);
        primitiveWrappers.add(Short.class);
        primitiveWrappers.add(Void.class);
    }

    private void printFieldInfo(Object value, StringBuilder sb, Field declaredField) {
        sb.append("fieldName: ").append(declaredField.getName())
                .append(" --- fieldValue: ").append(value)
                .append("\n");
    }

    private void printFieldInfo(Object value, StringBuilder sb, Class<?> clazz) {
        sb.append("fieldType: ").append(clazz)
                .append(" --- fieldValue: ").append(value)
                .append("\n");
    }

}
