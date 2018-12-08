package com.chriniko.jobinterview.livecoding.test;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        if (args.length != 1
                || !Arrays.asList("1", "2", "3").contains(args[0])) {
            System.out.println("Please enter number of exercise to run, selections: 1 or 2 or 3");
            System.exit(-1);
        }

        switch (args[0]){
            case "1":
                new FirstExercise().run();
                break;
            case "2":
                new SecondExercise().run();
                break;
            case "3":
                new ThirdExercise().run();
                break;
        }

    }
}
