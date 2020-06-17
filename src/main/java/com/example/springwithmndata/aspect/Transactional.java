package com.example.springwithmndata.aspect;

import io.micronaut.transaction.TransactionDefinition;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Transactional {
    TransactionDefinition.Propagation propagation() default TransactionDefinition.Propagation.REQUIRED;
    TransactionDefinition.Isolation isolationLevel() default TransactionDefinition.Isolation.DEFAULT;
    long timeout() default -1;
    boolean readOnly() default false;
    String name() default "";
}
