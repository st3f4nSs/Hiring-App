package com.company;

class Marketing extends Department {

    public Marketing() {
        super();
    }

    @Override
    public double getTotalSalaryBudget() {
        int i;
        double sum;

        sum = 0d;
        for (i = 0; i < employees.size(); i++) {
            if (employees.get(i).salary > 5000)
                sum += employees.get(i).salary + 0.1 * employees.get(i).salary;
            else if (employees.get(i).salary <= 5000 && employees.get(i).salary >= 3000)
                sum += employees.get(i).salary + 0.16 * employees.get(i).salary;
            else
                sum += employees.get(i).salary;
        }
        return sum;
    }
}
