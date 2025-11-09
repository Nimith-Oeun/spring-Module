/*
    # Represents a function that accepts one argument and produces a result.
    syntax: Function<input , output>
 */

package com.personal;

import java.util.function.Function;

public class FunctionalDemo {

    public static void main(String[] args) {

        Function<String, Integer> stringLength = String::length;
        Integer helloWorld = stringLength.apply("Hello World");
        System.out.println("Length of 'Hello World': " + helloWorld);

        //---------------------------------------

        Function<Integer, Integer> square = i -> i * i;
        Integer squaredValue = square.apply(5);
        System.out.println("Square of 5: " + squaredValue);

    }
}