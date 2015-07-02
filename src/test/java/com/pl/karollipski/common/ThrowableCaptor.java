package com.pl.karollipski.common;

import com.sun.org.apache.bcel.internal.generic.ExceptionThrower;

/*
 * That peac of code is not part of book tutorial,
 * but I am using Java 8 Lambdas to handle exceptions in tests.
 */
public class ThrowableCaptor {

    public interface Actor {
        void act() throws Throwable;
    }

    public static Throwable captureThrowable(Actor actor) {
        Throwable result = null;
        try {
            actor.act();
        } catch( Throwable throwable ) {
            result = throwable;
        }

        return result;
    }
}
