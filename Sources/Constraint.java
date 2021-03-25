package com.company;

import java.util.Objects;

class Constraint {
    public Double lower_bound;
    public Double upper_bound;

    public Constraint(Double upper_bound, Double lower_bound) {
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
    }

    public Constraint() {
        this.lower_bound = this.upper_bound = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Constraint that = (Constraint) o;
        return Objects.equals(lower_bound, that.lower_bound) &&
                Objects.equals(upper_bound, that.upper_bound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lower_bound, upper_bound);
    }
}
