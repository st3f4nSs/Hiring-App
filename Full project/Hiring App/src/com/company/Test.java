package com.company;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class Test {

    public static void main(String[] args) throws InvalidDatesException, FileNotFoundException, ResumeIncompleteException {
        MyJson myjson = new MyJson();
        ArrayList<Employee> employees;
        ArrayList<Recruiter> recruiters;
        ArrayList<User> users;
        ArrayList<Manager> managers;

        employees = new ArrayList<>();
        users = new ArrayList<>();
        recruiters = new ArrayList<>();
        managers = new ArrayList<>();
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader("consumers.json"));
        myjson = gson.fromJson(reader, MyJson.class);

        // Construim vectorul cu obiecte de tip employee

        for(int i = 0; i < myjson.employees.size(); i++) {
            String name[] = myjson.employees.get(i).name.split(" ");
            String last_name = name[1];
            String first_name = name[0];
            Information information = new Information(last_name, first_name, myjson.employees.get(i).email,
                    myjson.employees.get(i).phone, myjson.employees.get(i).date_of_birth, myjson.employees.get(i).genre,
                    myjson.employees.get(i).languages, myjson.employees.get(i).languages_level);
            Employee employee = new Employee(null, new ArrayList<>(), null,
                    (double) myjson.employees.get(i).salary);
            Consumer.Resume resume = new Consumer.Resume.ResumeBuilder(information, myjson.employees.get(i).education).
                    experiences(myjson.employees.get(i).experience).build();
            employee.resume = resume;

            for(int j = 0 ; j < myjson.employees.get(i).experience.size(); j++)
                if(myjson.employees.get(i).experience.get(j).end_date == null) {
                    employee.curent_company = myjson.employees.get(i).experience.get(j).company;
                    break;
                }
            employees.add(employee);
        }

        //Construim vector cu obiecte de tip user

        for(int i = 0; i < myjson.users.size(); i++) {
            String name[] = myjson.users.get(i).name.split(" ");
            String last_name = name[1];
            String first_name = name[0];
            Information information = new Information(last_name, first_name, myjson.users.get(i).email,
                    myjson.users.get(i).phone, myjson.users.get(i).date_of_birth, myjson.users.get(i).genre,
                    myjson.users.get(i).languages, myjson.users.get(i).languages_level);
            User user = new User(null, new ArrayList<>(), myjson.users.get(i).interested_companies);
            Consumer.Resume resume = new Consumer.Resume.ResumeBuilder(information, myjson.users.get(i).education).
                    experiences(myjson.users.get(i).experience).build();
            user.resume = resume;
            users.add(user);
        }

        //Construim vector cu obiecte de tip recruiter

        for(int i = 0; i < myjson.recruiters.size(); i++) {
            String name[] = myjson.recruiters.get(i).name.split(" ");
            String last_name = name[1];
            String first_name = name[0];
            Information information = new Information(last_name, first_name, myjson.recruiters.get(i).email,
                    myjson.recruiters.get(i).phone, myjson.recruiters.get(i).date_of_birth,
                    myjson.recruiters.get(i).genre, myjson.recruiters.get(i).languages,
                    myjson.recruiters.get(i).languages_level);
            Recruiter recruiter = new Recruiter(null, new ArrayList<>(), null,
                    (double) myjson.recruiters.get(i).salary);

            Consumer.Resume resume = new Consumer.Resume.ResumeBuilder(information, myjson.recruiters.get(i).education).
                    experiences(myjson.recruiters.get(i).experience).build();

            recruiter.resume = resume;

            for(int j = 0 ; j < myjson.recruiters.get(i).experience.size(); j++)
                if(myjson.recruiters.get(i).experience.get(j).end_date == null) {
                    recruiter.curent_company = myjson.recruiters.get(i).experience.get(j).company;
                    break;
                }
            for(int j = 0; j < recruiter.resume.experiences.size(); j++)
                if(recruiter.resume.experiences.get(j).position.equalsIgnoreCase("RECRUITER"))
                    recruiter.resume.experiences.get(j).department = "IT";
            recruiters.add(recruiter);
        }

        //Construim vector cu obiecte de tip manager

        for(int i = 0; i < myjson.managers.size(); i++) {
            String name[] = myjson.managers.get(i).name.split(" ");
            String last_name = name[1];
            String first_name = name[0];
            Information information = new Information(last_name, first_name, myjson.managers.get(i).email,
                    myjson.managers.get(i).phone, myjson.managers.get(i).date_of_birth, myjson.managers.get(i).genre,
                    myjson.managers.get(i).languages, myjson.managers.get(i).languages_level);
            Manager manager = new Manager(null, new ArrayList<>(), null,
                    (double) myjson.managers.get(i).salary, new ArrayList<>());
            Consumer.Resume resume = new Consumer.Resume.ResumeBuilder(information, myjson.managers.get(i).education).
                    experiences(myjson.managers.get(i).experience).build();

            manager.resume = resume;

            for(int j = 0 ; j < myjson.managers.get(i).experience.size(); j++)
                if(myjson.managers.get(i).experience.get(j).end_date == null) {
                    manager.curent_company = myjson.managers.get(i).experience.get(j).company;
                    break;
                }
            managers.add(manager);
        }

        //cream companii si departamente

        ArrayList<String> companies_name = new ArrayList<>();
        ArrayList<Company> companies = new ArrayList<>();
        companies_name.add("Google");
        companies_name.add("Amazon");

        // cream companiile si adaugam campurile corespunzatoare in ele.

        for(int i = 0; i < companies_name.size(); i++) {
            Company company = new Company();
            DepartmentFactory departmentFactory = new DepartmentFactory();
            Department it = departmentFactory.factory("IT");
            Department management = departmentFactory.factory("Management");
            Department finance = departmentFactory.factory("Finance");
            Department marketing = departmentFactory.factory("Marketing");

            company.name = companies_name.get(i);
            company.departments.add(it);
            company.departments.add(management);
            company.departments.add(finance);
            company.departments.add(marketing);

            for(Employee employee : employees) {
                if(employee.curent_company.equalsIgnoreCase(companies_name.get(i))) {
                    for(Experience experience : employee.resume.experiences)
                        if(experience.end_date == null) {
                            String department_name = experience.department;
                            for(Department department : company.departments)
                                if(department.getClass().getName().contains(department_name))
                                    department.add(employee);
                        }
                }
            }

            for(Recruiter recruiter : recruiters) {
                if(recruiter.curent_company.equalsIgnoreCase(company.name)) {
                    company.recruiters.add(recruiter);
                    for(Department department : company.departments)
                        if(department.getClass().getName().contains("IT"))
                            department.add(recruiter);
                }
            }

            for(Manager manager : managers) {
                if(manager.curent_company.equalsIgnoreCase(company.name))
                    company.manager = manager;
            }
            companies.add(company);
        }

        //Construirea retelei.
        users.get(0).social_list.add(users.get(1));
        users.get(0).social_list.add(employees.get(2));

        users.get(1).social_list.add(users.get(0));
        users.get(1).social_list.add(recruiters.get(0));
        users.get(1).social_list.add(employees.get(6));

        users.get(2).social_list.add(users.get(3));
        users.get(2).social_list.add(employees.get(2));

        users.get(3).social_list.add(users.get(2));
        users.get(3).social_list.add(employees.get(9));

        employees.get(1).social_list.add(employees.get(9));
        employees.get(1).social_list.add(recruiters.get(2));

        employees.get(2).social_list.add(users.get(0));
        employees.get(2).social_list.add(users.get(2));
        employees.get(2).social_list.add(employees.get(5));
        employees.get(2).social_list.add(recruiters.get(1));

        employees.get(5).social_list.add(employees.get(2));
        employees.get(5).social_list.add(recruiters.get(3));

        employees.get(9).social_list.add(users.get(3));
        employees.get(9).social_list.add(employees.get(1));

        employees.get(6).social_list.add(users.get(1));

        recruiters.get(0).social_list.add(users.get(1));
        recruiters.get(1).social_list.add(employees.get(2));
        recruiters.get(2).social_list.add(employees.get(1));
        recruiters.get(3).social_list.add(employees.get(5));


        // Initializarea unui obiect de tip Application
        Application api = Application.getInstance(companies, users);

        // parsarea json-ului pentru job-uri.
        JsonJob jsonJob = new JsonJob();
        reader = new JsonReader(new FileReader("jobs.json"));
        jsonJob = gson.fromJson(reader, JsonJob.class);
        ArrayList<Job> jobs = new ArrayList<>();
        for(Job job : jsonJob.jobs) {
            job.candidates = new ArrayList<>();
            jobs.add(job);
        }

        // adaug observerii pt fiecare companie.

        for(Company company : companies)
            for(User user : users) {
                if(user.company_wishlist.contains(company.name))
                    company.addObserver(user);
            }

        // Adaug job-urile in departamente.
        for(Job job : jobs) {
            for(Company company : companies)
                if(company.name.equalsIgnoreCase(job.company_name)) {
                    for(Department department : company.departments)
                        if(department.getClass().getName().contains("IT")) {
                            department.add(job);
                            company.notifyAllObservers("S-a adaugat un job la " + company.name + ": " +
                                    job.job_name);
                            break;
                        }
                    break;
                }
        }

        // aplica userii pentru
        System.out.println("\n\n\n");
        for(Job job : jobs) {
            for(User user : users) {
                job.apply(user);
            }
        }

        //Pentru a testa functia process din clasa manager.

        System.out.println(users.get(0).meanGPA() + " " + users.get(0).getGraduationYear() + " " + jobs.get(0).experience_years(users.get(0)));
        System.out.println(users.get(1).meanGPA() + " " + users.get(1).getGraduationYear() + " " + jobs.get(0).experience_years(users.get(1)));
        System.out.println(users.get(2).meanGPA() + " " + users.get(2).getGraduationYear() + " " + jobs.get(0).experience_years(users.get(2)));
        System.out.println(users.get(3).meanGPA() + " " + users.get(3).getGraduationYear() + " " + jobs.get(0).experience_years(users.get(3)));


        System.out.println("\n\n\n");
        api.companies.get(0).manager.process(jobs.get(0));
        api.companies.get(0).manager.process(jobs.get(1));
        api.companies.get(1).manager.process(jobs.get(2));
        api.companies.get(1).manager.process(jobs.get(3));

        int k = 5;   //Doar pentru a pune breakpoint si a testa cu JavaDebugger. Urmarim in api
                    // pentru fiecare companie la departamentul IT.


        /*JFrame frame = new JFrame("Job Application");
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setContentPane(new Login(frame).main);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/

    }
}

class JsonJob {
    ArrayList<Job> jobs = new ArrayList<>();
}

class MyField {
    public String name;
    public String email;
    public String phone;
    public String date_of_birth;
    public String genre;
    public ArrayList<String> languages;
    public ArrayList<String> languages_level;
    int salary;
    ArrayList<Education> education;
    ArrayList<Experience> experience;

}

class MyField2 {
    public String name;
    public String email;
    public String phone;
    public String date_of_birth;
    public String genre;
    public ArrayList<String> languages;
    public ArrayList<String> languages_level;
    public ArrayList<String> interested_companies;
    ArrayList<Education> education;
    ArrayList<Experience> experience;
}

class MyJson {
    public ArrayList<MyField> employees;
    public ArrayList<MyField> recruiters;
    public ArrayList<MyField2> users;
    public ArrayList<MyField> managers;

    public MyJson() {
        employees = new ArrayList<>();
        recruiters = new ArrayList<>();
        managers = new ArrayList<>();
        users = new ArrayList<>();
    }
}


class InvalidDatesException extends Exception {
    public InvalidDatesException() {
        super("Data invalida !");
    }
}


class ResumeIncompleteException extends Exception {
    public ResumeIncompleteException() {
        super("Campuri incomplete in Resume.");
    }
}



/*Recruiter rec1 = new Recruiter(null, new ArrayList<>(),  "Google", 1210d, 61d);
        Recruiter rec2 = new Recruiter(null, new ArrayList<>(),  "Amazon", 120d, 6d);
        Recruiter rec3 = new Recruiter(null, new ArrayList<>(),  "Bloomberg", 120d, 6d);
        Recruiter rec4 = new Recruiter(null, new ArrayList<>(),  "Microsoft", 120d, 6d);
        Recruiter rec5 = new Recruiter(null, new ArrayList<>(),  "Bitdefender", 120d, 6d);
        Recruiter rec6 = new Recruiter(null, new ArrayList<>(),  "Adobe", 120d, 6d);
        Recruiter rec7 = new Recruiter(null, new ArrayList<>(),  "LeetCode", 120d, 6d);

        rec1.social_list.add(rec2);
        rec1.social_list.add(rec3);
        rec3.social_list.add(rec4);
        rec2.social_list.add(rec4);
        rec4.social_list.add(rec5);
        rec5.social_list.add(rec6);
        int level = rec1.getDegreeInFriendship(rec6);
        System.out.println(level);
        */

