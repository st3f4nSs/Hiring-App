package com.company;

import java.util.Objects;

class Request<K, V> {
    private K key;
    private V value1, value2;
    private Double score;

    public Request(K key, V value1, V value2, Double score) {
        this.key = key;
        this.value1 = value1;
        this.value2 = value2;
        this.score = score;
    }

    public K getKey() {
        return key;
    }

    public V getValue1() {
        return value1;
    }

    public V getValue2() {
        return value2;
    }

    public Double getScore() {
        return score;
    }

    public String toString() {
        return "Key: " + key + " ; Value1: " + value1 + " ; Value2: " + value2 + " ; Score: " + score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request<?, ?> request = (Request<?, ?>) o;
        return Objects.equals(key, request.key) &&
                Objects.equals(value1, request.value1) &&
                Objects.equals(value2, request.value2) &&
                Objects.equals(score, request.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value1, value2, score);
    }
}
