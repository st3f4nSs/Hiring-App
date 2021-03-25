package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class Custom_panel {
    public JPanel customMain;
    public JTextField userName;
    public JTextField jobName;
    public JTextField recruiterName;
    public JButton acceptButton;
    public JButton refuseButton;
    private JTextField scoreField;

    public Custom_panel(Manager manager,String user, String recruiter, String job, String score) {
        Application api = Application.getInstance();
        userName.setText(user);
        recruiterName.setText(recruiter);
        jobName.setText(job);
        scoreField.setText(score);


        acceptButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Employee employee = null;
                User myUser = null;
                Job myJob = null;
                Company myCompany = null;
                Department myDepartment = null;

                // cautam user-ul.
                for(User u : api.users) {
                    String fullname = u.resume.information.getFirst_name() + " " +
                            u.resume.information.getLast_name();
                    if(fullname.equalsIgnoreCase(userName.getText())) {
                        employee = u.convert();
                        myUser = u;
                        break;
                    }
                }

                if(employee == null) {
                    JOptionPane.showMessageDialog(customMain, "You can't perform this action!");
                    return;
                }

                // cautam compania.
                for(Company company : api.companies)
                    if(company.name.equalsIgnoreCase(manager.curent_company)) {
                        myCompany = company;
                        break;
                    }

                for(Department department : myCompany.departments)
                    for(Job searchedJob : department.jobs) {
                        if(searchedJob.job_name.equalsIgnoreCase(jobName.getText())) {
                            myJob = searchedJob;
                            myDepartment = department;
                            break;
                        }
                    }
                employee.salary = myJob.job_salary;
                employee.curent_company = myJob.company_name;

                if(myJob.noPositions - 1 == 0) {
                    for(Iterator<Request<Job, Consumer>> iterator = manager.requests.iterator(); iterator.hasNext();) {
                        Request<Job, Consumer> req = iterator.next();
                        if(req.getKey().equals(myJob) && req.getValue1().equals(myUser)) {
                            iterator.remove();
                        }
                    }
                    myJob.noPositions = 0;
                    myJob.is_open = false;
                    api.users.remove(myUser);
                    myDepartment.add(employee);
                    customMain.setVisible(false);
                    myCompany.notifyAllObservers("Jobul " + myJob.job_name + " a fost inchis.");

                } else if(myJob.noPositions != 0){
                    for(Iterator<Request<Job, Consumer>> iterator = manager.requests.iterator(); iterator.hasNext();) {
                        Request<Job, Consumer> req = iterator.next();
                        if(req.getKey().equals(myJob) && req.getValue1().equals(myUser)) {
                            iterator.remove();
                        }
                    }
                    myJob.noPositions = myJob.noPositions - 1;
                    api.users.remove(myUser);
                    myDepartment.add(employee);
                    customMain.setVisible(false);

                } else if(myJob.noPositions == 0)
                    JOptionPane.showMessageDialog(customMain, "The job is closed.");
            }
        });

        refuseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Ai fost respins.");
                Company myCompany = null;

                // cautam compania.
                for(Company company : api.companies)
                    if(company.name.equalsIgnoreCase(manager.curent_company)) {
                        myCompany = company;
                        break;
                    }

                for(User u : api.users) {
                    String fullname = u.resume.information.getFirst_name() + " " +
                            u.resume.information.getLast_name();
                    if(fullname.equalsIgnoreCase(userName.getText())) {
                        myCompany.notifyObserver("Ai fost respins de la jobul " + jobName.getText() +
                                " din compania " + manager.curent_company, u);
                        break;
                    }
                }
                customMain.setVisible(false);
            }
        });
    }
}
