package com.company;

import java.util.ArrayList;
import java.util.List;

class Application {
    public ArrayList<Company> companies;
    public ArrayList<User> users;
    private static Application single_instance = null;

    private Application(ArrayList<Company> companies, ArrayList<User> users) {
        this.companies = companies;
        this.users = users;
    }

    public static Application getInstance(ArrayList<Company> companies, ArrayList<User> users) {
        if (single_instance == null)
            single_instance = new Application(companies, users);

        return single_instance;
    }

    public static Application getInstance() {
        return single_instance;
    }

    public ArrayList<Company> getCompanies() {
        return companies;
    }

    public Company getCompany(String name) {
        int i;

        for (i = 0; i < companies.size(); i++)
            if (companies.get(i).name.equals(name))
                return companies.get(i);
        return null;
    }

    public void add(Company company) {
        companies.add(company);
    }

    public void add(User user) {
        users.add(user);
    }

    public boolean remove(Company company) {
        return companies.remove(company);
    }

    public boolean remove(User user) {
        return users.remove(user);
    }

    public ArrayList<Job> getJobs(List<String> companies) {
        ArrayList<Job> result = new ArrayList<>();

        // lista cu nume de companii dorite;
        // lista de companii, data a clasei;
        // returnez un vector de job-uri disponibile.

        for (String i : companies)
            for (Company comp : this.companies)
                if (comp.name.equals(i)) {
                    ArrayList<Job> open_jobs = comp.getJobs();
                    for (Job job : open_jobs)
                        result.add(job);
                }
        return result;
    }
}
