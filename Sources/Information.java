package com.company;

import java.util.ArrayList;
import java.util.Objects;

class Information {
    private String last_name; // nume
    private String first_name; // prenume
    private String email;
    private String phone;
    private String birthday_date;
    private String gender;
    private ArrayList<String> languages; // limba + Beginner/ Advanced/ Experienced
    private ArrayList<String> languages_level; // limba + Beginner/ Advanced/ Experienced
    // separate prin spatiu.

    public Information(String last_name, String first_name, String email, String phone, String birthday_date
            , String gender, ArrayList<String> languages, ArrayList<String> languages_level) {

        this.languages = new ArrayList<>();
        this.languages_level = new ArrayList<>();
        this.last_name = last_name;
        this.first_name = first_name;
        this.email = email;
        this.phone = phone;
        this.birthday_date = birthday_date;
        this.gender = gender;
        this.languages = languages;
        this.languages_level = languages_level;
    }

    public Information() {
        this.last_name = "";
        this.first_name = "";
        this.email = "";
        this.phone = "";
        this.birthday_date = "";
        this.gender = "";
        this.languages = new ArrayList<>();
        this.languages_level = new ArrayList<>();
    }

    public String getLast_name() {
        return last_name;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getBirthday_date() {
        return birthday_date;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<String> getLanguages() {
        return languages;
    }

    public ArrayList<String> getLanguages_level() {
        return languages_level;
    }


    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setBirthday_date(String birthday_date) {
        this.birthday_date = birthday_date;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setLanguages(ArrayList<String> languages) {
        this.languages = languages;
    }

    public void setLanguages_level(ArrayList<String> languages_level) {
        this.languages_level = languages_level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Information that = (Information) o;
        return Objects.equals(last_name, that.last_name) &&
                Objects.equals(first_name, that.first_name) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(birthday_date, that.birthday_date) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(languages, that.languages) &&
                Objects.equals(languages_level, that.languages_level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(last_name, first_name, email, phone, birthday_date, gender, languages, languages_level);
    }

    @Override
    public String toString() {
        return "First name: " + first_name + "         " + "Last name: " + last_name + "\n" +
                "Email: " + email + "\n" +
                "Phone: " + phone + "\n" +
                "Birthday date: " + birthday_date + "\n" +
                "Gender: " + gender + "\n";
    }
}
