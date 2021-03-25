package com.company;

import java.util.ArrayList;
import java.util.Objects;

class Employee extends Consumer {
    public String curent_company;
    public Double salary;

    public Employee(Resume resume, ArrayList<Consumer> social_list, String curent_company, Double salary) {
        this.resume = resume;
        this.social_list = social_list;
        this.curent_company = curent_company;
        this.salary = salary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(curent_company, employee.curent_company) &&
                Objects.equals(salary, employee.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), curent_company, salary);
    }
}
