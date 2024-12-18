package org.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

public @interface TestCase {
    enum Priority {
        HIGH,
        MEDIUM,
        LOW,
        MISSING
    }
    Priority priority()  default Priority.MISSING;
    String author () default "MISSING";
}
