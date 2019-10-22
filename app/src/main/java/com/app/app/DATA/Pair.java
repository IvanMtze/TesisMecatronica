package com.app.app.DATA;

public class Pair<T, D> {
    public Pair(){}
    private T left;
    private D right;

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public D getRight() {
        return right;
    }

    public void setRight(D right) {
        this.right = right;
    }
}
