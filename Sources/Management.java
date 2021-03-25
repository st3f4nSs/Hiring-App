package com.company;

class Management extends Department {

    public Management() {
        super();
    }

    @Override
    public double getTotalSalaryBudget() {
        int i;
        double sum;

        sum = 0d;
        for (i = 0; i < employees.size(); i++) {
            sum += employees.get(i).salary + 0.16 * employees.get(i).salary;
        }
        return sum;
    }
}
