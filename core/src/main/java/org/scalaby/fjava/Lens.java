package org.scalaby.fjava;

import com.google.common.base.Function;
import com.google.common.base.Objects;

import javax.annotation.Nullable;

/**
 * Implementation of a functional lens that allows you to modify hierarchies of immutable objects in an immutable manner.
 * User: remeniuk
 */
public class Lens<A, B> implements Function<A, B> {

    public final Function<A, B> get;
    public final Function2<A, B, A> set;

    public Lens(Function<A, B> get, Function2<A, B, A> set) {
        this.get = get;
        this.set = set;
    }

    @Override
    public B apply(@Nullable A a) {
        return get.apply(a);
    }

    public A updated(A whole, B part) {
        return set.apply(whole, part);
    }

    public A mod(A a, Function<B, B> f) {
        return set.apply(a, f.apply(apply(a)));
    }

    public <C> Lens<C, B> compose(final Lens<C, A> that) {
        return new Lens<C, B>(new Function<C, B>() {
            @Override
            public B apply(@Nullable C c) {
                return get.apply(that.apply(c));
            }
        }, new Function2<C, B, C>() {
            @Override
            public C apply(C c, final B b) {
                return that.mod(c, new Function<A, A>() {
                    @Override
                    public A apply(@Nullable A a) {
                        return set.apply(a, b);
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
