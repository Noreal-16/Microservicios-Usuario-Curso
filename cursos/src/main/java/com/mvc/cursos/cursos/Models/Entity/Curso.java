package com.mvc.cursos.cursos.Models.Entity;

import com.mvc.cursos.cursos.Models.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="curso")
public class Curso {

    @Id
    @Column(name = "id_curso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_course")
    private List<Course_User> course_users;

    @Transient
    private List<User> users;

    public Curso(){
        course_users = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void addUser(Course_User course_user){
        course_users.add(course_user);
    }

    public void removeCourseUser(Course_User course_user){
        course_users.remove(course_user);
    }
}
