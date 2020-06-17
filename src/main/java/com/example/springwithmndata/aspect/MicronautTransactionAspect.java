package com.example.springwithmndata.aspect;

import io.micronaut.transaction.SynchronousTransactionManager;
import io.micronaut.transaction.support.DefaultTransactionDefinition;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

@Aspect
@Component
public class MicronautTransactionAspect {

    private final SynchronousTransactionManager<Connection> transactionManager;

    public MicronautTransactionAspect(SynchronousTransactionManager<Connection> transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(Transactional)")
    public Object wrapInTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
        Transactional transactional = getTransactionalAnnotation(joinPoint);

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(transactional.propagation());
        transactionDefinition.setIsolationLevel(transactional.isolationLevel());
        if (transactional.timeout() >= 0) {
            transactionDefinition.setTimeout(Duration.ofMillis(transactional.timeout()));
        }
        transactionDefinition.setReadOnly(transactional.readOnly());
        if (!"".equals(transactional.name())) {
            transactionDefinition.setName(transactional.name());
        }

        AtomicReference<Throwable> exceptionThrown = new AtomicReference<>();
        Object result = transactionManager.execute(transactionDefinition, status -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                exceptionThrown.set(throwable);
                status.setRollbackOnly();
                return null;
            }
        });
        
        if (exceptionThrown.get() != null) {
            throw exceptionThrown.get();
        }
        
        return result;
    }

    private Transactional getTransactionalAnnotation(ProceedingJoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget()
                .getClass()
                .getMethod(signature.getMethod().getName(),
                        signature.getMethod().getParameterTypes());
        return method.getAnnotation(Transactional.class);
    }
}
