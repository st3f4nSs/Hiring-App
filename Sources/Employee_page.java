package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Employee_page {
    private JButton logoutButton;
    public JPanel employeeMain;

    public Employee_page(JFrame frame) {
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Login(frame).main);
                frame.revalidate();
                frame.repaint();
            }
        });
    }
}
