package org.example;


import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

import static org.testng.Assert.*;

public class Main {
    public static void main(String[] args) {
        try {
            executeTestCases();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        System.out.println("========================================================================");
    }

    @TestCase(priority = TestCase.Priority.HIGH, author = "Aram")
    public static void testCase2() {
        System.out.println("Executing: TestCase2");
        int asd = 1;
        int qwe = 2;
        assertEquals(asd, qwe);
        System.out.println("This line won't be printed");
    }

    @TestCase(priority = TestCase.Priority.LOW, author = "Vahan")
    public static void testCase4() {
        System.out.println("Executing: TestCase4");
        int zxc = 4;
        int vbn = 5;
        assertNotEquals(zxc, vbn);
        System.out.println("This line will be printed");
    }

    @TestCase(priority = TestCase.Priority.MEDIUM, author = "Vahan")
    public static void testCase5() {
        System.out.println("Executing: TestCase5");
        boolean bool = true;
        assertFalse(bool, "boolean bool is " + bool + " :::");
    }

    @TestCase(priority = TestCase.Priority.MISSING, author = "Vahan")
    public void testCase3() {
        System.out.println("Test audio editor");
    }

    @TestCase(priority = TestCase.Priority.HIGH, author = "David")
    public static void testCase6() {
        System.out.println("Executing: TestCase6");
        double doubleNumber1 = 12.23;
        double doubleNumber2 = 12.23;
        assertEquals(doubleNumber1, doubleNumber2);
        System.out.println("Assertion error not risen");
    }

    @WrongAnnotation(wrongEnum = WrongAnnotation.WrongEnum.FAIL)
    public static void testCase7() {
        System.out.println("Executing: TestCase7");
    }

    public static void testCase8() {
        System.out.println("Executing: TestCase8");
    }

    public static void executeTestCases() {
        List<Method> testMethods = new ArrayList<>();

        for (Method method : Main.class.getMethods()) {
            for (Annotation annotation : method.getAnnotations()) {
                if (annotation != method.getAnnotation(TestCase.class)) {
                    System.out.println("========================================================================");
                    System.out.printf("The %s method annotation is wrong, the annotation name is %s\n", method.getName(), annotation);
                }
            }
        }


        for (Method method : Main.class.getMethods()) {
            if (method.isAnnotationPresent(TestCase.class)) {
                testMethods.add(method);
            }
        }

        testMethods.sort((method1, method2) -> {
            TestCase.Priority priority1 = method1.getAnnotation(TestCase.class).priority();
            TestCase.Priority priority2 = method2.getAnnotation(TestCase.class).priority();
            return priority1.compareTo(priority2);
        });

        for (Method method : testMethods) {
            TestCase annotation = method.getAnnotation(TestCase.class);
            if (annotation.priority() != TestCase.Priority.MISSING) {
                try {
                    System.out.println("========================================================================");
                    System.out.println("Executing: " + method.getName() +
                            ", Priority: " + annotation.priority() +
                            ", Author: " + annotation.author());
                    method.invoke(null);
                } catch (Exception e) {
                    System.out.println("Test failed: " + method.getName() + " - " + e.getCause());
                }
            } else {
                System.out.println("========================================================================");
                System.out.printf("The %s does not have priority, priority is %s", method.getName(), annotation.priority());
            }
        }
    }
}

