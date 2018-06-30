package ru.otus.task1;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * To start the application:
 * mvn package
 * java -cp ./target/L01.1-maven.jar ru.otus.l011.Main
 * java -jar ./target/L01.1-maven.jar //java.lang.NoClassDefFoundError: com/google/common/collect/Lists
 * java -cp ./target/L01.1-maven.jar;C:\Users\vitaly.chibrikov\.m2\repository\com\google\guava\guava\23.0\guava-23.0.jar ru.otus.l011.Main
 *
 * To unzip the jar:
 * 7z x -oJAR ./target/L01.1-maven.jar
 * unzip -d JAR ./target/L01.1-maven.jar
 */
public class Main {
    public static void main(String... args) {
        List<String> input = Arrays.asList(args);
        Collections.shuffle(input);
        System.out.println(input);
    }
}