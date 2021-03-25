package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

class Job {
    public String job_name;
    public String company_name;
    public boolean is_open;
    Constraint grad_year;
    Constraint exp_years;
    Constraint academic_mean;
    ArrayList<User> candidates;
    int noPositions;
    double job_salary;

    public Job(String job_name, String company_name, boolean is_open, Constraint grad_year, Constraint exp_years,
               Constraint academic_mean, ArrayList<User> candidates, int noPositions, double job_salary) {
        this.job_name = job_name;
        this.company_name = company_name;
        this.is_open = is_open;
        this.grad_year = grad_year;
        this.exp_years = exp_years;
        this.academic_mean = academic_mean;
        this.candidates = candidates;
        this.noPositions = noPositions;
        this.job_salary = job_salary;
    }

    public void apply(User user) {
        Application api = Application.getInstance();
        int i;
        Recruiter recruiter = null;
        for (i = 0; i < api.companies.size(); i++)
            if (this.company_name.equals(api.companies.get(i).name)) {
                recruiter = api.companies.get(i).getRecruiter(user);
                break;
            }
        if (recruiter != null)
            recruiter.evaluate(this, user);
    }

    public boolean meetsRequirments(User user) {
        double inf, minus_inf;

        inf = 9999999;
        minus_inf = -9999999;
        if (grad_year.lower_bound == null)
            grad_year.lower_bound = minus_inf;
        if (grad_year.upper_bound == null)
            grad_year.upper_bound = inf;

        if (academic_mean.lower_bound == null)
            academic_mean.lower_bound = minus_inf;
        if (academic_mean.upper_bound == null)
            academic_mean.upper_bound = inf;

        if (exp_years.lower_bound == null)
            exp_years.lower_bound = minus_inf;
        if (exp_years.upper_bound == null)
            exp_years.upper_bound = inf;

        if (user.getGraduationYear() == null && (grad_year.lower_bound != minus_inf || grad_year.upper_bound != inf))
            return false;
        if (user.getGraduationYear() != null)
            if (!(user.getGraduationYear() >= grad_year.lower_bound && user.getGraduationYear() <= grad_year.upper_bound))
                return false;
        if (!(user.meanGPA() >= academic_mean.lower_bound && user.meanGPA() <= academic_mean.upper_bound))
            return false;
        if (!(this.experience_years(user) >= exp_years.lower_bound && this.experience_years(user) <= exp_years.upper_bound))
            return false;
        return true;
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

    public int experience_years(User user) {
        int i, years, months;

        years = 0;
        months = 0;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String current_date = dateFormat.format(date);
        for (i = 0; i < user.resume.experiences.size(); i++) {
            ArrayList<Integer> diff = new ArrayList<>();
            if (user.resume.experiences.get(i).end_date == null)
                diff = get_difference_day_month_year(user.resume.experiences.get(i).start_date, current_date);
            else
                diff = get_difference_day_month_year(user.resume.experiences.get(i).start_date,
                        user.resume.experiences.get(i).end_date);
            years += diff.get(2);
            months += diff.get(1);
        }
        if (months > 12) {
            while (months > 12) {
                years++;
                months -= 12;
            }
        }
        if (months >= 1)
            years++;
        return years;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Job job = (Job) o;
        return is_open == job.is_open &&
                noPositions == job.noPositions &&
                Double.compare(job.job_salary, job_salary) == 0 &&
                Objects.equals(job_name, job.job_name) &&
                Objects.equals(company_name, job.company_name) &&
                Objects.equals(grad_year, job.grad_year) &&
                Objects.equals(exp_years, job.exp_years) &&
                Objects.equals(academic_mean, job.academic_mean) &&
                Objects.equals(candidates, job.candidates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(job_name, company_name, is_open, grad_year, exp_years, academic_mean, candidates,
                noPositions, job_salary);
    }

    @Override
    public String toString() {
        return "Job name: " + job_name + "\n" +
                "Company name: " + company_name + "\n" +
                "Is open: " + is_open + "\n" +
                "Number of positions: " + noPositions + "\n" +
                "Job salary: " + job_salary + "\n\n";
    }
}
