package org.osgl.ut;

public class Foo {
    private String id;
    private int n;

    public Foo(String id, int n) {
        this.id = id;
        this.n = n;
    }

    public String getId() {
        return id;
    }

    public int getN() {
        return n;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Foo foo = (Foo) o;

        if (n != foo.n) return false;
        return id != null ? id.equals(foo.id) : foo.id == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + n;
        return result;
    }

    @Override
    public String toString() {
        return "Foo{" +
                "id='" + id + '\'' +
                ", n=" + n +
                '}';
    }
}
