package ru.otus;

import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

import java.lang.reflect.Method;
import java.util.List;

public class TestRunner {
    public void run(Class[] classes) {
        for (Class clazz: classes) {
            List<Method> tests = ReflectionHelper.getMethodsWithAnnotation(clazz, Test.class);
            List<Method> befores = ReflectionHelper.getMethodsWithAnnotation(clazz, Before.class);
            List<Method> afters = ReflectionHelper.getMethodsWithAnnotation(clazz, After.class);

            tests.stream().forEach(test -> {
                Object testObject = ReflectionHelper.instantiate(clazz);
                befores.stream().forEach(before -> ReflectionHelper.callMethod(testObject, before.getName()));
                ReflectionHelper.callMethod(testObject, test.getName());
                afters.stream().forEach(after -> ReflectionHelper.callMethod(testObject, after.getName()));
            });
        }
    }
}
