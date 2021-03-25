package com.company;

import java.util.ArrayList;
import java.util.Objects;

class Recruiter extends Employee {
    Double rating;

    public Recruiter(Resume resume, ArrayList<Consumer> social_list, String curent_company, Double salary) {
        super(resume, social_list, curent_company, salary);
        this.rating = 5d;
    }

    /*
        Am creat un request, pe care l-am adaugat in lista de request-uri a managerului.
        M-am folosit de numele companiei si de faptul ca aplicatia e singleton pentru a
            gasi managerul.

     */

    public int evaluate(Job job, User user) {
        Double score = 0d;
        score = user.getTotalScore() + this.rating;
        this.rating += 0.1;
        Request<Job, Consumer> request = new Request<>(job, user, this, score);
        Application api = Application.getInstance();
        if (job.meetsRequirments(user)) {
            for (int i = 0; i < api.companies.size(); i++) {
                if (api.companies.get(i).name.equalsIgnoreCase(job.company_name)) {
                    api.companies.get(i).manager.requests.add(request);
                    break;
                }
            }
        } else {
            for (Company company : api.companies)
                if (job.company_name.equalsIgnoreCase(company.name)) {
                    String notification = "Ai fost respins de la job-ul: " + job.job_name + " din compania " +
                            job.company_name + ".";
                    company.notifyObserver(notification, user);
                    break;
                }
        }
        return score.intValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Recruiter recruiter = (Recruiter) o;
        return Objects.equals(rating, recruiter.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), rating);
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "resume=" + resume +
                '}';
    }
}
