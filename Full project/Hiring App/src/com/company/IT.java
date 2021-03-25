package com.company;

class IT extends Department {

    public IT() {
        super();
    }

    @Override
    public double getTotalSalaryBudget() {
        int i;
        Double sum;

        sum = 0d;
        for (i = 0; i < employees.size(); i++) {
            sum += employees.get(i).salary;
        }
        return sum;
    }
}
