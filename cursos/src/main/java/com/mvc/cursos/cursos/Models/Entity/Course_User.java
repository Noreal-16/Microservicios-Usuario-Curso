package com.mvc.cursos.cursos.Models.Entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "course_user")
@Getter
@Setter
public class Course_User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private Long id_user;

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return  true;
        }
        if (!(obj instanceof  Course_User)){
            return  false;
        }
        Course_User course_users = (Course_User) obj;
        return this.id_user != null && this.id_user.equals(course_users.id_user);
    }
}
