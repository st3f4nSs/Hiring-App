package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class User_page {
    public JPanel userMain;
    public JTabbedPane tabbedPane1;
    public JPanel homePanel;
    public JPanel notificationPanel;
    public JPanel searchPanel;
    public JPanel titlePanel;
    public JTextField userName;
    public JTextArea profileArea;
    public JPanel dataPanel;
    private JButton logoutButton;
    private JPanel logoutPanel;
    private JScrollPane areaScrollPane;
    private JTextArea languagesArea;
    private JTextArea educationArea;
    private JTextArea experienceArea;
    private JTextField searchedUser;
    private JButton searchButton;
    private JList<String> jList;
    private JTextArea infoArea;

    public User_page(JFrame frame, User user) {
        userName.setText(user.resume.information.getFirst_name() + " " +
                user.resume.information.getLast_name());

        Information information = user.resume.information;
        String info = "Information:\n" + "Name: " + information.getFirst_name() + " " + information.getLast_name() +
                "\n" + "Email: " + information.getEmail() + " \n" + "Phone: " + information.getPhone() + "\n"
                + "Birthday date: " + information.getBirthday_date() + "\n" + "Gender: " + information.getGender()
                + "\n" + "Known languages: " + information.getLanguages() + "\n"
                + "Languages level: " + information.getLanguages_level() + "\n\n\n";

        String education = "Education: \n";
        for(int i = 0; i < user.resume.studies.size(); i++) {
            String edu = "Start date: " + user.resume.studies.get(i).start_date + "\n"
                    + "End date: " + user.resume.studies.get(i).end_date + "\n"
                    + "Institution name: " + user.resume.studies.get(i).name + "\n"
                    + "Level: " + user.resume.studies.get(i).level + "\n"
                    + "Grade: " + user.resume.studies.get(i).grade + "\n\n";
            education = education + edu;
        }
        education += "\n";

        String experience = "Experience: \n";
        for(int i = 0; i < user.resume.experiences.size(); i++) {
            String exp = "Start date: " + user.resume.experiences.get(i).start_date + "\n"
                    + "End date: " + user.resume.experiences.get(i).end_date + "\n"
                    + "Company: " + user.resume.experiences.get(i).company + "\n"
                    + "Department: " + user.resume.experiences.get(i).department + "\n"
                    + "Position: " + user.resume.experiences.get(i).position + "\n\n";
            experience = experience + exp;
        }

        profileArea.setText(info + education + experience);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setContentPane(new Login(frame).main);
                frame.revalidate();
                frame.repaint();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Application api = Application.getInstance();
                Consumer consumer = null;
                if(!searchedUser.getText().equals("")) {
                    for(User myuser : api.users) {
                        String fullname = myuser.resume.information.getFirst_name() + " " +
                                myuser.resume.information.getLast_name();
                        if(fullname.equalsIgnoreCase(searchedUser.getText())) {
                            consumer = myuser;
                            break;
                        }
                    }
                    if(consumer == null)
                        for(Company company : api.companies)
                            for(Department department : company.departments)
                                for(Employee employee : department.employees) {
                                    String fullname = employee.resume.information.getFirst_name() + " " +
                                            employee.resume.information.getLast_name();
                                    if(fullname.equalsIgnoreCase(searchedUser.getText())) {
                                        consumer = employee;
                                        break;
                                    }
                                }
                    if(consumer == null) {
                        for(Company company : api.companies) {
                            String fullname = company.manager.resume.information.getFirst_name() + " " +
                                    company.manager.resume.information.getLast_name();
                            if(fullname.equalsIgnoreCase(searchedUser.getText())) {
                                consumer = company.manager;
                                break;
                            }
                        }
                    }
                }
                if(consumer == null)
                    JOptionPane.showMessageDialog(userMain, "Nu exista un utilizator cu acest nume.");
                else {

                    infoArea.setText(consumer.resume.information.toString());
                    String languages = "";
                    for(int i = 0; i < consumer.resume.information.getLanguages().size(); i++) {
                        if(i != consumer.resume.information.getLanguages().size() - 1)
                            languages += consumer.resume.information.getLanguages().get(i) + "(" +
                                    consumer.resume.information.getLanguages_level().get(i) + ")" + ", ";
                        else
                            languages += consumer.resume.information.getLanguages().get(i) + "(" +
                                    consumer.resume.information.getLanguages_level().get(i) + ")" + ".";
                    }
                    languagesArea.setText(languages);
                    String educations = "";
                    for(int i = 0 ; i < consumer.resume.studies.size(); i++)
                        educations += consumer.resume.studies.get(i).toString();
                    educationArea.setText(educations);

                    String experiences = "";
                    for(int i = 0; i < consumer.resume.experiences.size(); i++)
                        experiences += consumer.resume.experiences.get(i).toString();
                    experienceArea.setText(experiences);
                }
            }
        });

        DefaultListModel<String> listModel = new DefaultListModel<>();
        for(int i = 0; i < user.notifications.size(); i++)
            listModel.addElement(user.notifications.get(i));
        jList.setModel(listModel);
        jList.setCellRenderer(new NotificationRenderer());

    }
}

class NotificationRenderer extends JLabel implements ListCellRenderer<String> {

    public NotificationRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends String> list, String value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        ImageIcon imageIcon = new ImageIcon("notification.png");
        ImageIcon imageIcon2 = new ImageIcon("notification2.jpg");
        setText(value);
        if(index % 2 == 0)
            setIcon(imageIcon);
        else
            setIcon(imageIcon2);

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        return this;
    }

}
