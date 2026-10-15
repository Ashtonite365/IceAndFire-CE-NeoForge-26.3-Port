package com.iafenvoy.iceandfire.util;

/**
 * Drop-in replacement for net.minecraft.util.Tuple, which was removed in MC 26.3.
 * Same API (getA/getB/setA/setB, constructor); just change the import.
 */
public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return this.a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return this.b;
    }

    public void setB(B b) {
        this.b = b;
    }
}
