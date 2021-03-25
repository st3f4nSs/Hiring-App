package com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

class User extends Consumer {
    ArrayList<String> company_wishlist;
    ArrayList<String> notifications = new ArrayList<>();

    public User(Resume resume, ArrayList<Consumer> social_list, ArrayList<String> company_wishlist) {
        super.resume = resume;
        super.social_list = social_list;
        this.company_wishlist = company_wishlist;
    }

    public Employee convert() {
        Employee employee = new Employee(resume, social_list, null, null);
        return employee;
    }

    public void update(String notification) {
        this.notifications.add(notification);
    }

    // date1 < date2
    /*
        Datele le-am considerat string-uri.

        Functia returneaza un ArrayList cu diferenta din cele 2 date(reprezentate ca string-uri):
            pe pozitia 0 o sa avem zilele, pe pozitia 1 lunile si pe ultima pozitie anii.
     */
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

    /*
        Functia get_date primeste un string si intoare un vector care are:
            pe pozitia 0 zilele, pe pozitia 1 lunile si pe ultima pozitie anii.
     */
    private ArrayList<Integer> get_date(String date) {
        ArrayList<Integer> array = new ArrayList<>();

        String dates[] = date.split("/");

        array.add(Integer.parseInt(dates[0]));
        array.add(Integer.parseInt(dates[1]));
        array.add(Integer.parseInt(dates[2]));

        return array;
    }

    /*
        Am iterat prin lista de experiente si m-am folosit de functiile prezentate mai sus
            pentru a calcula numarul de ani de experienta. In cazul in care end_date-ul
            experientei era null, am facut diferenta dintre start_date si data curenta.

        La final am transformati lunile in ani si apoi am aproximat prin adaos.
     */

    public Double getTotalScore() {
        int i, years, months;

        years = 0;
        months = 0;
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String current_date = dateFormat.format(date);
        for (i = 0; i < resume.experiences.size(); i++) {
            ArrayList<Integer> diff = new ArrayList<>();
            if (resume.experiences.get(i).end_date == null)
                diff = get_difference_day_month_year(resume.experiences.get(i).start_date, current_date);
            else
                diff = get_difference_day_month_year(resume.experiences.get(i).start_date,
                        resume.experiences.get(i).end_date);
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

        Double result;
        result = 1.5 * years + meanGPA();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(company_wishlist, user.company_wishlist);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), company_wishlist);
    }

    @Override
    public String toString() {
        return "User{" +
                "resume=" + resume +
                '}';
    }
}
