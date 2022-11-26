package com.mvc.cursos.cursos.Repository;

import com.mvc.cursos.cursos.Models.Entity.Curso;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends CrudRepository<Curso, Long> {
}
