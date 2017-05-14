package me.wbars.life.util;

import java.util.Objects;

public class Pair<T1, T2> {
    public final T1 f;
    public final T2 s;

    public Pair(T1 f, T2 s) {
        this.f = Objects.requireNonNull(f);
        this.s = Objects.requireNonNull(s);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!f.equals(pair.f)) return false;
        return s.equals(pair.s);
    }

    @Override
    public int hashCode() {
        int result = f.hashCode();
        result = 31 * result + s.hashCode();
        return result;
    }
}
