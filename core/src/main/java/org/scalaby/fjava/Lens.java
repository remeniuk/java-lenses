package org.scalaby.fjava;

import com.google.common.base.Function;
import com.google.common.base.Objects;

import javax.annotation.Nullable;

/**
 * Implementation of a functional lens that allows you to modify hierarchies of immutable objects in an immutable manner.
 * User: remeniuk
 */
public class Lens<A, B> implements Function<A, B> {

    public Function<A, B> fget;
    public Function2<A, B, A> fset;

    public Lens() {

    }

    public Lens(Function<A, B> fget, Function2<A, B, A> fset) {
        this.fget = fget;
        this.fset = fset;
    }

    public B get(A a) {
        return fget.apply(a);
    }

    public A set(A a, B b) {
        return fset.apply(a, b);
    }

    @Override
    public B apply(@Nullable A a) {
        return get(a);
    }

    public A updated(A whole, B part) {
        return set(whole, part);
    }

    public A mod(A a, Function<B, B> f) {
        return set(a, f.apply(apply(a)));
    }

    public <C> Lens<C, B> compose(final Lens<C, A> that) {
        return new Lens<C, B>(new Function<C, B>() {
            @Override
            public B apply(@Nullable C c) {
                return get(that.apply(c));
            }
        }, new Function2<C, B, C>() {
            @Override
            public C apply(C c, final B b) {
                return that.mod(c, new Function<A, A>() {
                    @Override
                    public A apply(@Nullable A a) {
                        return set(a, b);
                    }
                });
            }
        }
        );
    }

    public <C> Lens<A, C> andThen(Lens<B, C> that) {
        return that.compose(this);
    }

    @Override
    public boolean equals(@Nullable Object o) {
        return Objects.equal(this, o);
    }

}
