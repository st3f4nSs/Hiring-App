package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Admin_page {
    public JPanel adminMain;
    public JComboBox<String> companyBox;
    public JComboBox<String> departmentBox;
    public JComboBox<String> employeeBox;
    public JButton buttonRun;
    public JTextField totalSalaryField;
    public JTextArea employeeArea;
    public JButton logoutButton;
    public JTabbedPane tabbedPane1;
    public JPanel searchPanel;
    public JButton searchButton;
    public JTextArea languagesArea;
    public JTextArea educationArea;
    public JTextArea experienceArea;
    public JTextArea infoArea;
    public JTextArea jobsArea;
    public JComboBox<String> userSearchBox;
    String companyBoxCommand;
    String departmentBoxCommand;
    String employeeBoxCommand;
    String userBoxCommand;

    public Admin_page(JFrame frame) {
        Application api = Application.getInstance();

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Login(frame).main);
                frame.revalidate();
                frame.repaint();
            }
        });

        for(Company company : api.companies)
            companyBox.addItem(company.name);

        departmentBox.addItem("IT");
        departmentBox.addItem("Management");
        departmentBox.addItem("Marketing");
        departmentBox.addItem("Finance");

        for(User user : api.users)
            userSearchBox.addItem(user.resume.information.getFirst_name() + " " +
                    user.resume.information.getLast_name());

        companyBoxCommand = (String) companyBox.getSelectedItem();
        departmentBoxCommand = (String) departmentBox.getSelectedItem();
        userBoxCommand = (String) userSearchBox.getSelectedItem();

        for(Company company : api.companies) {
            if(company.name.equalsIgnoreCase((String) companyBox.getSelectedItem()))
                for(Department department : company.departments) {
                    if(department.getClass().getName().contains((String) departmentBox.getSelectedItem())) {
                        for(Employee employee : department.employees)
                            employeeBox.addItem(employee.resume.information.getFirst_name() + " " +
                                    employee.resume.information.getLast_name());
                    }
                }
        }
        employeeBoxCommand = (String) employeeBox.getSelectedItem();
        companyBox.setSelectedIndex(0);
        departmentBox.setSelectedIndex(0);
        employeeBox.setSelectedIndex(0);

        companyBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                companyBoxCommand = (String) companyBox.getSelectedItem();
                employeeBox.removeAllItems();
                for(Company company : api.companies) {
                    if(company.name.equalsIgnoreCase(companyBoxCommand))
                        for(Department department : company.departments) {
                            if(department.getClass().getName().contains(departmentBoxCommand)) {
                                for(Employee employee : department.employees)
                                    employeeBox.addItem(employee.resume.information.getFirst_name() + " " +
                                            employee.resume.information.getLast_name());
                            }
                        }
                }
                employeeBoxCommand = (String) employeeBox.getSelectedItem();
            }
        });

        departmentBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                departmentBoxCommand = (String) departmentBox.getSelectedItem();
                employeeBox.removeAllItems();
                for(Company company : api.companies) {
                    if(company.name.equalsIgnoreCase(companyBoxCommand))
                        for(Department department : company.departments) {
                            if(department.getClass().getName().contains(departmentBoxCommand)) {
                                for(Employee employee : department.employees)
                                    employeeBox.addItem(employee.resume.information.getFirst_name() + " " +
                                            employee.resume.information.getLast_name());
                            }
                        }
                }
                employeeBoxCommand = (String) employeeBox.getSelectedItem();

            }
        });


        employeeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                employeeBoxCommand = (String) employeeBox.getSelectedItem();
            }
        });

        buttonRun.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee myEmployee = null;
                String fulltext = null;

                for(Company company : api.companies)
                    if (company.name.equalsIgnoreCase(companyBoxCommand))
                        for (Department department : company.departments)
                            if (department.getClass().getName().contains(departmentBoxCommand)) {
                                totalSalaryField.setText(String.valueOf(department.getTotalSalaryBudget()));
                                for (Employee employee : department.employees) {
                                    String fullname = employee.resume.information.getFirst_name() + " " +
                                            employee.resume.information.getLast_name();
                                    if (fullname.equalsIgnoreCase(employeeBoxCommand)) {
                                        myEmployee = employee;
                                        break;
                                    }
                                }
                            }
                Information information = myEmployee.resume.information;
                String info = "Information:\n" + "Name: " + information.getFirst_name() + " " +
                        information.getLast_name() + "\n" + "Email: " + information.getEmail() +
                        " \n" + "Phone: " + information.getPhone() + "\n" + "Birthday date: " +
                        information.getBirthday_date() + "\n" + "Gender: " + information.getGender()
                        + "\n" + "Known languages: " + information.getLanguages() + "\n"
                        + "Languages level: " + information.getLanguages_level() + "\n" +
                        "Company: " + myEmployee.curent_company + "\nSalary: " + myEmployee.salary + "\n\n\n";

                String education = "Education: \n";
                for (int i = 0; i < myEmployee.resume.studies.size(); i++) {
                    String edu = "Start date: " + myEmployee.resume.studies.get(i).start_date + "\n"
                            + "End date: " + myEmployee.resume.studies.get(i).end_date + "\n"
                            + "Institution name: " + myEmployee.resume.studies.get(i).name + "\n"
                            + "Level: " + myEmployee.resume.studies.get(i).level + "\n"
                            + "Grade: " + myEmployee.resume.studies.get(i).grade + "\n\n";
                    education = education + edu;
                }
                education += "\n";

                String experience = "Experience: \n";
                for (int i = 0; i < myEmployee.resume.experiences.size(); i++) {
                    String exp = "Start date: " + myEmployee.resume.experiences.get(i).start_date + "\n"
                            + "End date: " + myEmployee.resume.experiences.get(i).end_date + "\n"
                            + "Company: " + myEmployee.resume.experiences.get(i).company + "\n"
                            + "Department: " + myEmployee.resume.experiences.get(i).department + "\n"
                            + "Position: " + myEmployee.resume.experiences.get(i).position + "\n\n";
                    experience = experience + exp;
                }
                fulltext = info + education + experience;
                employeeArea.setText(fulltext);

                String allJobs = "";
                for(Company company : api.companies) {
                    if(company.name.equalsIgnoreCase(companyBoxCommand))
                        for(Department department : company.departments) {
                            if(department.getClass().getName().contains(departmentBoxCommand)) {
                                for(Job job : department.jobs)
                                    allJobs += job.toString();
                            }
                        }
                }
                jobsArea.setText(allJobs);
            }
        });

        userSearchBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                userBoxCommand = (String) userSearchBox.getSelectedItem();
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application api = Application.getInstance();
                User user = null;

                for(User myuser : api.users) {
                    String fullname = myuser.resume.information.getFirst_name() + " " +
                            myuser.resume.information.getLast_name();
                    if(fullname.equalsIgnoreCase(userBoxCommand)) {
                        user = myuser;
                        break;
                    }
                }
                infoArea.setText(user.resume.information.toString());
                String languages = "";
                for(int i = 0; i < user.resume.information.getLanguages().size(); i++) {
                    if(i != user.resume.information.getLanguages().size() - 1)
                        languages += user.resume.information.getLanguages().get(i) + "(" +
                                user.resume.information.getLanguages_level().get(i) + ")" + ", ";
                    else
                        languages += user.resume.information.getLanguages().get(i) + "(" +
                                user.resume.information.getLanguages_level().get(i) + ")" + ".";
                }
                languagesArea.setText(languages);
                String educations = "";
                for(int i = 0 ; i < user.resume.studies.size(); i++)
                    educations += user.resume.studies.get(i).toString();
                educationArea.setText(educations);

                String experiences = "";
                for(int i = 0; i < user.resume.experiences.size(); i++)
                    experiences += user.resume.experiences.get(i).toString();
                experienceArea.setText(experiences);
            }
        });
    }
}