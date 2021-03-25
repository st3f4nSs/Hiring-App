package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    public JPanel main;
    private JTextField username;
    private JButton button1;
    private JPasswordField password;
    private JButton adminButton;

    public Login(JFrame frame) {
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = new String(password.getPassword());

                Application api = Application.getInstance();
                for(User user : api.users) {
                    String fullname = user.resume.information.getFirst_name() + " " +
                            user.resume.information.getLast_name();
                    if(fullname.equalsIgnoreCase(username.getText()) && pass.equalsIgnoreCase("user")) {
                        frame.setContentPane(new User_page(frame, user).userMain);
                        frame.revalidate();
                        frame.repaint();
                        return ;
                    }
                }

                for(Company company : api.companies) {
                    String fullname = company.manager.resume.information.getFirst_name() + " " +
                            company.manager.resume.information.getLast_name();
                    if(fullname.equalsIgnoreCase(username.getText()) && pass.equalsIgnoreCase("manager")) {
                        frame.setContentPane(new Manager_page(frame, company.manager).managerMain);
                        frame.revalidate();
                        frame.repaint();
                        return ;
                    }
                }

                for(Company company : api.companies)
                    for(Department department : company.departments)
                        for(Employee employee : department.employees) {
                            String fullname = employee.resume.information.getFirst_name() + " " +
                                    employee.resume.information.getLast_name();
                            if(fullname.equalsIgnoreCase(username.getText()) && pass.equalsIgnoreCase("employee")) {
                                frame.setContentPane(new Employee_page(frame).employeeMain);
                                frame.revalidate();
                                frame.repaint();
                                return ;
                            }
                        }


                JOptionPane.showMessageDialog(main, "Username or password are wrong.");
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pass = new String(password.getPassword());
                if(!username.getText().equalsIgnoreCase("admin") ||
                        !pass.equalsIgnoreCase("admin"))
                    JOptionPane.showMessageDialog(main, "Username or password are wrong.");
                else {
                    frame.setContentPane( new Admin_page(frame).adminMain);
                    frame.revalidate();
                    frame.repaint();
                    return;
                }
            }
        });
    }
}
