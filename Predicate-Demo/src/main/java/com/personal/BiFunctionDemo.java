/*
    # Represents a function that accepts two arguments and produces a result.
    syntax: BiFunction<input , input , output>
 */

package com.personal;

import java.util.function.BiFunction;

public class BiFunctionDemo {

    public static void main(String[] args) {

        BiFunction<String , Integer , Integer> power = (s , i) -> (int)Math.pow(s.length(), i);
        Integer hello = power.apply("Hello", 2);
        System.out.println(hello);
    }
}