package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class MyAnnotationsTest {

    private Integer number;

    @Before
    public void fillNumber() {
        System.out.println("Before was called!");
        number = 42;
    }

    @Test
    public void testNumber() {
        assert (number.equals(21*2));
        System.out.println("Assertion done!");
    }

    @After
    public void afterMethod() {
        System.out.println("All ok!");
    }
}
