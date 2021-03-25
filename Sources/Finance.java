package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

class Finance extends Department {

    public Finance() {
        super();
    }

    @Override
    public double getTotalSalaryBudget() {
        int i, j;
        double sum;

        sum = 0d;
        for (i = 0; i < employees.size(); i++) {
            int years, months, days;
            years = months = days = 0;
            for (j = 0; j < employees.get(i).resume.experiences.size(); j++) {
                String start_date = employees.get(i).resume.experiences.get(j).start_date;
                String end_date = employees.get(i).resume.experiences.get(j).end_date;
                ArrayList<Integer> diff;
                if (end_date == null) {
                    Date date = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    String current_date = dateFormat.format(date);
                    diff = this.get_difference_day_month_year(start_date, current_date);
                } else
                    diff = this.get_difference_day_month_year(start_date, end_date);
                years += diff.get(2);
                months += diff.get(1);
                days += diff.get(0);
            }
            if (days > 30)
                while (days > 30) {
                    months++;
                    days -= 30;
                }
            if (months > 12)
                while (months > 12) {
                    years++;
                    months -= 12;
                }
            if (years < 1)
                sum += employees.get(i).salary + employees.get(i).salary * 0.10;
            else
                sum += employees.get(i).salary + employees.get(i).salary * 0.16;
        }
        return sum;
    }

    private ArrayList<Integer> get_difference_day_month_year(String date1, String date2) {
        ArrayList<Integer> date_start = this.get_date(date1);
        ArrayList<Integer> date_end = this.get_date(date2);

        LocalDate start_date = LocalDate.of(date_start.get(2), date_start.get(1), date_start.get(0));
        LocalDate end_date = LocalDate.of(date_end.get(2), date_end.get(1), date_end.get(0));

        Period diff = Period.between(start_date, end_date);
        ArrayList<Integer> result = new ArrayList<>();
        result.add(diff.getDays());
        result.add(diff.getMonths());
        result.add(diff.getYears());
        return result;
    }

    private ArrayList<Integer> get_date(String date) {
        ArrayList<Integer> array = new ArrayList<>();

        String dates[] = date.split("/");

        array.add(Integer.parseInt(dates[0]));
        array.add(Integer.parseInt(dates[1]));
        array.add(Integer.parseInt(dates[2]));

        return array;
    }

}
