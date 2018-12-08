package com.chriniko.jobinterview.livecoding.test;

import java.util.HashMap;
import java.util.Map;

class FirstExercise {


    private String paragraph = "The digest surfaces above your thorough breed. " +
            "Inside the desktop flips the appraisal. The zero temperature scratches. " +
            "With an excess gradual spins the verified arithmetic. " +
            "The sordid stiff buttons a microcomputer." +
            "The kid mutters behind the signal railway." +
            " A rotated desire arrives over the resemblance." +
            " A verb noses behind our smashing distress. The sample burns the comedy " +
            "within the versatile indicator. Does the chicken ruin a discriminating exercise?";

    /*
        Note: write a program which will print the word occurrences of a provided paragraph.
     */
    void run() {

        Map<String, Integer> occurrences = new HashMap<>();

        paragraph = paragraph.replaceAll("\\.", "");

        for (String word : paragraph.split(" ")) {
            occurrences.putIfAbsent(word, 0);
            occurrences.computeIfPresent(word, (w, o) -> o + 1);
        }

        occurrences
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .forEach(e -> System.out.println("word: " + e.getKey() + " --- occurrence: " + e.getValue()));

        System.out.println();

        occurrences
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .ifPresent(e -> System.out.println("Word with max occurrences is: " + e.getKey()));

    }

}
