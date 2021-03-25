package com.company;

import java.util.ArrayList;
import java.util.Objects;

abstract class Department {
    public ArrayList<Employee> employees;
    public ArrayList<Job> jobs;

    public Department() {
        employees = new ArrayList<>();
        jobs = new ArrayList<>();
    }

    public abstract double getTotalSalaryBudget();

    //• Metodă care întoarce toate joburile deschise din departament;
// flag e de tip boolean(true = deschis, false = inchis)
    public ArrayList<Job> getJobs() {
        int i;
        ArrayList<Job> result = new ArrayList<>();

        for (i = 0; i < jobs.size(); i++)
            if (jobs.get(i).is_open)
                result.add(jobs.get(i));

        return result;
    }

    //• Metodă care adăuga un angajat în departament;
    public void add(Employee employee) {
        employees.add(employee);
    }

    //• Metodă care s, terge un angajat din departament;
    public void remove(Employee employee) {
        employees.remove(employee);
    }

    //• Metodă care adaugă un job în departament;
    public void add(Job job) {
        jobs.add(job);
    }

    //• Metodă care întoarce angajat, ii dintr-un departament;
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return Objects.equals(employees, that.employees) &&
                Objects.equals(jobs, that.jobs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employees, jobs);
    }
}

class DepartmentFactory {
    public Department factory(String type) {
        if(type == null)
            return null;

        if(type.equalsIgnoreCase("IT"))
            return new IT();

        else if(type.equalsIgnoreCase("MANAGEMENT"))
            return new Management();

        else if(type.equalsIgnoreCase("MARKETING"))
            return new Marketing();

        else if(type.equalsIgnoreCase("FINANCE"))
            return new Finance();

        return null;
    }
}
