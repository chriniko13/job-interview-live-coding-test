package com.chriniko.jobinterview.livecoding.test;

class ThirdExercise {

    /**
     * Define a function that prints a reverse string using recursion
     * e.g. asdf -> fdsa (using recursion)
     */
    void run() {

        System.out.println("Hello Java");

        printReverse("Hello Java", 0);
    }

    private void printReverse(String s, int idx) {

        if (idx < s.length() - 1) {
            printReverse(s, idx + 1);
        }
        System.out.print(s.charAt(idx));
    }
}
