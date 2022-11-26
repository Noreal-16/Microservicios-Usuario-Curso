package com.mvc.cursos.cursos.Service;

import com.mvc.cursos.cursos.Models.Entity.Curso;
import com.mvc.cursos.cursos.Models.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CursoService {

    List<Curso> getAllCurso();

    Optional<Curso> getByIdCurso(long id);

    Curso saveCurso(Curso curso);

    void deleteCuros(long id);

    Optional<User> asignarUsuario(User user, Long cursoId);
    Optional<User> createUser(User user, Long cursoId);
    Optional<User> deleteUserOfCourse(User user, Long courseId);
}
