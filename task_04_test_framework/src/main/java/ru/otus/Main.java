package ru.otus;

public class Main {
    public static void main(String[] args) {
        TestRunner testRunner = new TestRunner();
        Class[] classes = {MyAnnotationsTest.class};
        testRunner.run(classes);
    }
}
