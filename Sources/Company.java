package com.company;

import java.util.ArrayList;
import java.util.Objects;

class Company {
    public String name;
    public Manager manager;
    public ArrayList<Recruiter> recruiters;
    public ArrayList<Department> departments;
    public ArrayList<User> users = new ArrayList<>();

    public Company(String name, Manager manager, ArrayList<Recruiter> recruiters, ArrayList<Department> departments) {
        this.name = name;
        this.manager = manager;
        this.recruiters = recruiters;
        this.departments = departments;
    }

    public Company() {
        name = "";
        manager = new Manager();
        recruiters = new ArrayList<>();
        departments = new ArrayList<>();
    }

    public void addObserver(User user) {
        users.add(user);
    }

    public void removeObserver(User user) {
        users.remove(user);
    }

    public void notifyAllObservers(String notification) {
        for (User user : users) {
            user.update(notification);
        }
    }

    public void notifyObserver(String notification, User user) {
        user.update(notification);
    }

    public void add(Department department) {
        departments.add(department);
    }

    public void add(Recruiter recruiter) {
        recruiters.add(recruiter);
    }

    public void add(Employee employee, Department department) {
        department.employees.add(employee);
    }

    public void remove(Employee employee) {
        int i;

        for (i = 0; i < departments.size(); i++) {
            departments.get(i).employees.remove(employee);
        }
    }

    public void remove(Department department) {

        int index;

        index = departments.indexOf(department);
        departments.get(index).employees.clear();
        departments.get(index).jobs.clear();
        departments.remove(department);
    }

    public void remove(Recruiter recruiter) {
        recruiters.remove(recruiter);
    }

    public void move(Department source, Department destination) {
        int i;

        for (i = 0; i < source.employees.size(); i++) {
            destination.employees.add(source.employees.get(i));
        }
        for (i = 0; i < source.jobs.size(); i++) {
            destination.jobs.add(source.jobs.get(i));
        }
        this.remove(source);
    }

    public void move(Employee employee, Department newDepartment) {
        remove(employee);
        newDepartment.employees.add(employee);
    }

    public boolean contains(Department department) {
        return departments.contains(department);
    }

    public boolean contains(Employee employee) {
        int i;

        for (i = 0; i < departments.size(); i++)
            if (departments.get(i).employees.contains(employee))
                return true;
        return false;
    }

    public boolean contains(Recruiter recruiter) {
        return recruiters.contains(recruiter);
    }

    public Recruiter getRecruiter(User user) {
        int i;
        Recruiter best_choice = null;
        int max_level = 0;

        for (i = 0; i < this.recruiters.size(); i++) {
            int level = user.getDegreeInFriendship(this.recruiters.get(i));
            if (level > max_level) {
                best_choice = this.recruiters.get(i);
                max_level = level;
            } else if (max_level == level)
                if (best_choice.rating < this.recruiters.get(i).rating)
                    best_choice = this.recruiters.get(i);
        }
        return best_choice;
    }

    public ArrayList<Job> getJobs() {
        ArrayList<Job> result = new ArrayList<>();
        int i, j;

        for (i = 0; i < departments.size(); i++) {
            for (j = 0; j < departments.get(i).jobs.size(); j++)
                if (departments.get(i).jobs.get(j).is_open)
                    result.add(departments.get(i).jobs.get(j));
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(name, company.name) &&
                Objects.equals(manager, company.manager) &&
                Objects.equals(recruiters, company.recruiters) &&
                Objects.equals(departments, company.departments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, manager, recruiters, departments);
    }
}
