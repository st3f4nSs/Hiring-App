package com.company;

import java.util.*;

abstract class Consumer {
    public Resume resume;
    public ArrayList<Consumer> social_list;

    public void add(Education education) {
        resume.studies.add(education);
    }

    //Adăugarea unei experiente profesionale;
    public void add(Experience experience) {
        resume.experiences.add(experience);
    }

    //Adăugarea unui nou cunoscut;
    public void add(Consumer consumer) {
        social_list.add(consumer);
    }

    /*
        Determinarea gradului de prietenie cu un alt utilizator – se realizează o parcurgere
            în lătime în reteaua socială a utilizatorului;
        Am folosit o coada si 2 hashmap-uri: unul pentru a marca consumerii deja vizitati si
            celalalt pentru a calcula nivelul unui consumer in functie de nivelul parintelui
            dupa formula nivel_consumer = nivel_parinte + 1;

        PS: era suficient un singur hashmap, dar la momentul implementarii nu mi-am dat seama.
    */
    public int getDegreeInFriendship(Consumer consumer) {
        Queue<Consumer> queue = new LinkedList<>();
        Map<Consumer, Integer> level = new HashMap<>();
        Map<Consumer, Integer> mark_visited = new HashMap<>();

        queue.add(this);
        level.put(this, 0);
        mark_visited.put(this, 1);
        while (!queue.isEmpty()) {

            Consumer cons = queue.peek();
            if (cons.equals(consumer))
                return level.get(consumer);
            queue.remove();
            for (int i = 0; i < cons.social_list.size(); i++) {
                if (!mark_visited.containsKey(cons.social_list.get(i))) {       // daca nu e marcat
                    mark_visited.put(cons.social_list.get(i), 1);               // il marcam
                    level.put(cons.social_list.get(i), level.get(cons) + 1);    // ii adaugam nivelul
                    queue.add(cons.social_list.get(i));
                }
            }
        }
        return -1;          // in cazul in care nu-l gasim.
    }

    //Eliminarea unei persoane din reteaua socială;
    public void remove(Consumer consumer) {
        social_list.remove(consumer);
    }

    //Determinarea anului absolvirii;
    public Integer getGraduationYear() {
        int year;

        year = 0;
        for (int i = 0; i < resume.studies.size(); i++) {
            if (resume.studies.get(i).end_date != null &&
                    resume.studies.get(i).level.equalsIgnoreCase("college")) {
                return get_year(resume.studies.get(i).end_date);
            }
        }
        return null;
    }

    //Determinarea mediei studiilor absolvite;
    public Double meanGPA() {
        Double sum;
        int cnt;

        cnt = 0;
        sum = 0d;
        for (int i = 0; i < resume.studies.size(); i++) {
            if (resume.studies.get(i).grade != null) {
                sum += resume.studies.get(i).grade;
                cnt++;
            }
        }
        sum = sum / cnt;
        return sum;
    }

    private int get_year(String date) {
        int year;

        String array[] = date.split("/");
        year = Integer.parseInt(array[2]);
        return year;
    }

    public static class Resume {
        public Information information;
        ArrayList<Education> studies;
        ArrayList<Experience> experiences;

        public Resume(ResumeBuilder builder) throws ResumeIncompleteException {
            this.information = builder.information;
            this.studies = builder.educations;
            this.experiences = builder.experiences;
            Collections.sort(this.studies);
            Collections.sort(this.experiences);
            if (this.information == null || this.studies.isEmpty())
                throw new ResumeIncompleteException();
        }

        public static class ResumeBuilder {
            private Information information;
            private ArrayList<Education> educations;
            ArrayList<Experience> experiences;

            public ResumeBuilder(Information information, ArrayList<Education> educations) {
                this.information = information;
                this.educations = educations;
            }

            public ResumeBuilder experiences(ArrayList<Experience> experiences) {
                this.experiences = experiences;
                return this;
            }

            public Resume build() throws ResumeIncompleteException {
                return new Resume(this);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Resume resume = (Resume) o;
            return Objects.equals(information, resume.information) &&
                    Objects.equals(studies, resume.studies) &&
                    Objects.equals(experiences, resume.experiences);
        }

        @Override
        public int hashCode() {
            return Objects.hash(information, studies, experiences);
        }

        @Override
        public String toString() {
            return "Resume{" +
                    "information=" + information +
                    ", studies=" + studies +
                    ", experiences=" + experiences +
                    '}';
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumer consumer = (Consumer) o;
        return Objects.equals(resume, consumer.resume) &&
                Objects.equals(social_list, consumer.social_list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resume);
    }
}
