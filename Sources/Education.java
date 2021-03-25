package com.company;

import java.util.ArrayList;
import java.util.Objects;

class Education implements Comparable<Education> {
    public String start_date;
    public String end_date;
    public String name; //institution name
    public String level; //liceu, licenta, master, doctorat
    public Double grade;

    public Education(String start_date, String end_date, String name, String level,
                     Double grade) throws InvalidDatesException {

        if (end_date != null) {
            if (this.isValid(start_date, end_date) != 1)
                throw new InvalidDatesException();
        }
        this.start_date = start_date;
        this.end_date = end_date;
        this.name = name;
        this.level = level;
        this.grade = grade;
    }

    public Education() throws InvalidDatesException {
        this(null, null, null, null, null);
    }

    private ArrayList<Integer> get_date(String date) {
        ArrayList<Integer> mydate = new ArrayList<>();
        int day, month, year;

        String array[] = date.split("/");
        day = Integer.parseInt(array[0]);
        month = Integer.parseInt(array[1]);
        year = Integer.parseInt(array[2]);
        mydate.add(day);
        mydate.add(month);
        mydate.add(year);
        return mydate;
    }

    private int isValid(String start_date, String end_date) {
        ArrayList<Integer> start = this.get_date(start_date);
        ArrayList<Integer> end = this.get_date(end_date);

        if (this.compare_dates(start, end) == -1)
            return 1;
        else
            return 0;
    }

    //    1 - first date bigger
    //    0 - they are equal
    //    -1 - second is bigger


    private int compare_dates(ArrayList<Integer> start_date, ArrayList<Integer> end_date) {
        if (start_date.get(2) > end_date.get(2))
            return 1;
        else if (start_date.get(2) < end_date.get(2))
            return -1;

        if (start_date.get(1) > end_date.get(1))
            return 1;
        else if (start_date.get(1) < end_date.get(1))
            return -1;

        if (start_date.get(0) > end_date.get(0))
            return 1;
        else if (start_date.get(0) < end_date.get(0))
            return -1;

        return 0;
    }


    @Override
    public int compareTo(Education education) {
        int comp;

        if (this.end_date == null || education.end_date == null) {
            ArrayList<Integer> start_date = this.get_date(this.start_date);
            ArrayList<Integer> start_date2 = this.get_date(education.start_date);
            comp = this.compare_dates(start_date, start_date2);
            return comp;

        } else {
            ArrayList<Integer> end_date = this.get_date(this.end_date);
            ArrayList<Integer> end_date2 = this.get_date(education.end_date);
            comp = this.compare_dates(end_date, end_date2);
            if (comp == 0)
                return (-1) * this.grade.compareTo(education.grade);
            else
                return (-1) * comp;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Education education = (Education) o;
        return Objects.equals(start_date, education.start_date) &&
                Objects.equals(end_date, education.end_date) &&
                Objects.equals(name, education.name) &&
                Objects.equals(level, education.level) &&
                Objects.equals(grade, education.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start_date, end_date, name, level, grade);
    }

    @Override
    public String toString() {
        return "Start date = " + start_date + "\n" +
                "End date = " + end_date + "\n" +
                "Name = " + name + "\n" +
                "Level = " + level + "\n" +
                "Grade = " + grade + "\n\n";
    }
}
