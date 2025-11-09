/*
    # Resprasents a Predicate (Boolean-valued function) in Java.
 */

package com.personal;

import java.util.function.Predicate;

public class PridecateDemo {

    public static void main(String[] args) {

        Predicate<String> containA =s -> s.contains("A");
        boolean test = containA.test("ABC");
        System.out.println(test);
   }
}