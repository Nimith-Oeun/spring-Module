/*
    # Represents an operation that accepts a single input argument and returns no result.

 */

package com.personal;

import java.util.List;
import java.util.function.Consumer;

public class ConsumerDemo {

    public static void main(String[] args) {

        Consumer<String> nameConsumer = name -> System.out.println("Hello, " + name + "!");
        nameConsumer.accept("Nimith");

        //----------------------------
        System.out.println("=".repeat(10) + "List of Values As ArrayStyle" + "=".repeat(10));

        List<Integer>values = List.of(1, 2, 3, 4, 5);
        Consumer<List<Integer>>detailValues = list -> {
            System.out.println("List of Values:");
            System.out.println(list);
        };
        detailValues.accept(values);

        //----------------------------
        System.out.println("=".repeat(10) + "List of Values As ForEach Style" + "=".repeat(10));

        Consumer<Integer>detailValues2 = list -> System.out.println(list);
//        values.forEach(detailValues2);
//        values.forEach(n -> System.out.print("Value: " + n));
        values.forEach(System.out::println);

        //----------------------------
        System.out.println("=".repeat(10) + "List of Values As ForEach Style with Even and Odd" + "=".repeat(10));

//        for (int  i =0 ; i< values.size(); i++) {
//            System.out.println("Value: " + values.get(i));
//            if (values.get(i) %2 == 0) {
//                System.out.println("Even Value: " + values.get(i));
//            } else {
//                System.out.println("Odd Value: " + values.get(i));
//            }
//        }

        //----------------------------

        Consumer<Integer>detailValues3 = list -> {
            if (list % 2 == 0) {
                System.out.println("Even Value: " + list);
            } else {
                System.out.println("Odd Value: " + list);
            }
        };
        values.forEach(detailValues3);
    }
}