package org.scalaby.fjava;

/**
 * Binary function
 * User: remeniuk
 */
public interface Function2<A, B, C> {

    public C apply(A a, B b);

}
