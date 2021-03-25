package com.company;

import java.util.*;

class Manager extends Employee {
    ArrayList<Request<Job, Consumer>> requests;

    public Manager(Resume resume, ArrayList<Consumer> social_list, String curent_company, Double salary,
                   ArrayList<Request<Job, Consumer>> requests) {
        super(resume, social_list, curent_company, salary);
        this.requests = new ArrayList<>(requests);
    }

    public Manager() {
        this(null, new ArrayList<>(), null, null, new ArrayList<>());
    }

    /*
        Construim un vector de request-uri ce contine doar request-urile pentru jobul job(dat ca parametru)
            care va fi sortat crescator dupa scor. Concomitent vom sterge request-urile salvate din
            vectorul de request-uri ale managerului.
        Verificam daca userul mai exista in lista de useri ai aplicatiei.
        Vom crea employee-ul si ii vom completa campurile salary si company.
        Cautam compania dupa nume in lista de companii si adaugam employee-ul in departamentul corespunzator
        job-ului.
        Daca toate pozitiile au fost ocupate trimitem o notificare observatorilor.

        |||Explicatii si in interiorul codului|||

     */
    public void process(Job job) {
        ArrayList<Request<Job, Consumer>> job_requests = new ArrayList<>();
        Comparator<Request<Job, Consumer>> by_score = new Comparator<Request<Job, Consumer>>() {
            @Override
            public int compare(Request<Job, Consumer> o1, Request<Job, Consumer> o2) {
                return (-1) * o1.getScore().compareTo(o2.getScore());
            }
        };
        Application api = Application.getInstance();
        //Selectam doar job-urile ce contin job-ul "job" in request.

        for (Iterator<Request<Job, Consumer>> iterator = requests.iterator(); iterator.hasNext(); ) {
            Request<Job, Consumer> req = iterator.next();
            if (req.getKey().equals(job)) {
                job_requests.add(req);
                iterator.remove();
            }
        }
        //sortam vectorul dupa scor descrescator.

        Collections.sort(job_requests, by_score);
        int i = 0;
        int cnt = 0;
        while (cnt < job.noPositions && i < job_requests.size()) {
            User user = null;

            //castam consumer-ul la user.
            if (job_requests.get(i).getValue1() instanceof User)
                user = (User) job_requests.get(i).getValue1();

            // verificam daca user-ul mai exista in lista de useri ai aplicatiei
            if (api.users.contains(user)) {
                for (Company company : api.companies) {
                    if (company.name.equalsIgnoreCase(this.curent_company)) {
                        company.removeObserver(user);
                        break;
                    }
                }
                Employee new_employee = user.convert();

                // cream angajatul
                // modificam campurile nule: salariu si companie
                new_employee.salary = job.job_salary;
                new_employee.curent_company = job.company_name;
                api.users.remove(user);

                // determinam compania => obiect de tip Company
                Company company = null;
                for (int j = 0; j < api.companies.size(); j++)
                    if (job.company_name.equals(api.companies.get(j).name)) {
                        company = api.companies.get(j);
                        break;
                    }
                // cautam job-ul prin departamente si la final adaugam angajatul in departament.
                for (int j = 0; j < company.departments.size(); j++)
                    if (company.departments.get(j).jobs.contains(job)) {
                        company.departments.get(j).add(new_employee);
                        break;
                    }
                cnt++;
            }
            i++;
        }
        if(job.noPositions - cnt == 0) {
            job.is_open = false;
            job.noPositions = 0;
            for (Company company : api.companies)
                if (company.name.equalsIgnoreCase(this.curent_company))
                    company.notifyAllObservers("Jobul" + job.job_name + " a fost inchis.");

        } else {
                job.noPositions = job.noPositions - cnt;
            }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Manager manager = (Manager) o;
        return Objects.equals(requests, manager.requests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), requests);
    }
}
