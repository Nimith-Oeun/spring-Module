/*
    # Represents an operation on a single operand that produces a result of the same type as its operand.
 */

package com.personal;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class UnaryOpearatorDemo {

    public static void main(String[] args) {

        UnaryOperator<String> toUpperCase = s -> s.toUpperCase();
        String applied = toUpperCase.apply("hello world");
        System.out.println(applied);

        //---------------------------

        UnaryOperator<Integer> square = i -> i * i;
        Integer squared = square.apply(5);
        System.out.println(squared);

        //---------------------------
        System.out.println("=".repeat(10)+"Coverting String to Integer"+"=".repeat(10));

        Function<String , Integer> stoi = String::length;
        Function<Integer,Integer> value = i -> i * i;
        Integer convert = stoi.andThen(value).apply("Nimith");
        System.out.println(convert);
    }
}