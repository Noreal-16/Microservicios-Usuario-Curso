package com.mvc.cursos.cursos.Service.Impl;

import com.mvc.cursos.cursos.Clients.UserClientRest;
import com.mvc.cursos.cursos.Models.Entity.Course_User;
import com.mvc.cursos.cursos.Models.Entity.Curso;
import com.mvc.cursos.cursos.Models.User;
import com.mvc.cursos.cursos.Repository.CursoRepository;
import com.mvc.cursos.cursos.Service.CursoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    private final UserClientRest userClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> getAllCurso() {
        return (List<Curso>) cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> getByIdCurso(long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional
    public Curso saveCurso(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void deleteCuros(long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> asignarUsuario(User user, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            User userMscv = userClientRest.detailUser(user.getId());

            Curso curso = cursoOptional.get();
            Course_User course_user = new Course_User();
            course_user.setId_user(userMscv.getId());

            curso.addUser(course_user);
            cursoRepository.save(curso);
            return Optional.of(userMscv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long cursoId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(cursoId);
        if (cursoOptional.isPresent()) {
            User userMscv = userClientRest.createUser(user);

            Curso curso = cursoOptional.get();
            Course_User course_user = new Course_User();
            course_user.setId_user(userMscv.getId());

            curso.addUser(course_user);
            cursoRepository.save(curso);
            return Optional.of(userMscv);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> deleteUserOfCourse(User user, Long courseId) {
        Optional<Curso> cursoOptional = cursoRepository.findById(courseId);
        if (cursoOptional.isPresent()) {
            User userMscv = userClientRest.detailUser(user.getId());

            Curso curso = cursoOptional.get();
            Course_User course_user = new Course_User();
            course_user.setId_user(userMscv.getId());

            curso.removeCourseUser(course_user);
            cursoRepository.save(curso);
            return Optional.of(userMscv);
        }
        return Optional.empty();
    }

}
