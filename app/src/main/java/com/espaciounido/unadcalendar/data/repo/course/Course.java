package com.espaciounido.unadcalendar.data.repo.course;


import java.util.List;

/**
 * Created by MyMac on 5/09/16.
 */
public class Course {
    public List<Curso> cursos;

    public class Curso {
        public String name;
        public String code;
        public String credits;
        public String period;
    }
}
