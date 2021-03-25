package com.company;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.*;

public class Manager_page {
    public JPanel managerMain;
    private JTabbedPane tabbedPane1;
    private JPanel requestPanel;
    private JButton logoutButton;
    private JPanel FinalAdd;
    private JButton runButton;
    private JComboBox<String> departmentBox;
    private JComboBox<String> employeeBox;
    private JTextArea infoArea;
    private JScrollPane scrollPanel;
    String departmentBoxCommand;
    String employeeBoxCommand;

    public Manager_page(JFrame frame, Manager manager) {
        Application api = Application.getInstance();

        FinalAdd.setLayout(new BoxLayout(FinalAdd, BoxLayout.Y_AXIS));
        for(Request<Job, Consumer> request : manager.requests) {
            String user = request.getValue1().resume.information.getFirst_name() + " " + request.getValue1().resume
                    .information.getLast_name();
            String recruiter = request.getValue2().resume.information.getFirst_name() + " " + request.getValue2().
                    resume.information.getLast_name();
            String job = request.getKey().job_name;
            String score = String.valueOf(request.getScore().doubleValue());
            Custom_panel customPanel = new Custom_panel(manager, user, recruiter, job, score);
            FinalAdd.add(customPanel.customMain);
        }


        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Login(frame).main);
                frame.revalidate();
                frame.repaint();
            }
        });

        departmentBox.addItem("IT");
        departmentBox.addItem("Finance");
        departmentBox.addItem("Marketing");
        departmentBox.addItem("Management");

        departmentBoxCommand = (String) departmentBox.getSelectedItem();
        for(Company company : api.companies) {
            if(company.name.equalsIgnoreCase(manager.curent_company))
                for(Department department : company.departments) {
                    if(department.getClass().getName().contains((String) departmentBox.getSelectedItem())) {
                        for(Employee employee : department.employees)
                            employeeBox.addItem(employee.resume.information.getFirst_name() + " " +
                                    employee.resume.information.getLast_name());
                    }
                }
        }
        employeeBoxCommand = (String) employeeBox.getSelectedItem();
        departmentBox.setSelectedIndex(0);
        employeeBox.setSelectedIndex(0);


        departmentBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                departmentBoxCommand = (String) departmentBox.getSelectedItem();
                employeeBox.removeAllItems();
                for(Company company : api.companies) {
                    if(company.name.equalsIgnoreCase(manager.curent_company))
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


        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee myEmployee = null;
                String fulltext = null;

                for(Company company : api.companies)
                    if (company.name.equalsIgnoreCase(manager.curent_company))
                        for (Department department : company.departments)
                            if (department.getClass().getName().contains(departmentBoxCommand)) {
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
                infoArea.setText(fulltext);
            }
        });
        tabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                departmentBoxCommand = (String) departmentBox.getSelectedItem();
                employeeBox.removeAllItems();
                for(Company company : api.companies) {
                    if(company.name.equalsIgnoreCase(manager.curent_company))
                        for(Department department : company.departments) {
                            if(department.getClass().getName().contains((String) departmentBox.getSelectedItem())) {
                                for(Employee employee : department.employees)
                                    employeeBox.addItem(employee.resume.information.getFirst_name() + " " +
                                            employee.resume.information.getLast_name());
                            }
                        }
                }
                employeeBoxCommand = (String) employeeBox.getSelectedItem();
            }
        });
    }
}
