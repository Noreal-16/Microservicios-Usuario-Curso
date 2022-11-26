package com.mvc.cursos.cursos.Controller;

import com.mvc.cursos.cursos.Models.Entity.Curso;
import com.mvc.cursos.cursos.Models.User;
import com.mvc.cursos.cursos.Service.CursoService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@AllArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @GetMapping
    public List<Curso> findAllUser() {
        return cursoService.getAllCurso();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdCors(@PathVariable long id) {
        Optional<Curso> cursoOptional = cursoService.getByIdCurso(id);
        if (cursoOptional.isPresent()) {
            return ResponseEntity.ok(cursoOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> createCurso(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            return validaciones(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.saveCurso(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@Valid @PathVariable long id, BindingResult result, @RequestBody Curso curso) {
        if (result.hasErrors()) {
            return validaciones(result);
        }
        Optional<Curso> cursoOptional = cursoService.getByIdCurso(id);
        if (cursoOptional.isPresent()) {
            Curso cursoDb = cursoOptional.get();
            cursoDb.setName(curso.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(cursoService.saveCurso(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourseByID(@PathVariable long id) {
        Optional<Curso> cursoOptional = cursoService.getByIdCurso(id);
        if (cursoOptional.isPresent()) {
            cursoService.deleteCuros(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody User user, @PathVariable long cursoId) {
        Optional<User> userOptional;
        /**
         * Try y catch es para validar la comunicacion
         */
        try {
            userOptional = cursoService.asignarUsuario(user, cursoId);
        } catch (FeignException error) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(Collections.singletonMap("Error", "No se encuentra usuaio por el Id o Error en la comunicacion " + error.getMessage()));
        }
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody User user, @PathVariable long cursoId) {
        Optional<User> userOptional;
        /**
         * Try y catch es para validar la comunicacion
         */
        try {
            userOptional = cursoService.createUser(user, cursoId);
        } catch (FeignException error) {
           return ResponseEntity.status(HttpStatus.NOT_FOUND)
                   .body(Collections.singletonMap("Error", "No se pudo crear el usuario o Error en la comunicacion " + error.getMessage()));
        }
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> deleteUsuario(@RequestBody User user, @PathVariable long cursoId) {
        Optional<User> userOptional;
        /**
         * Try y catch es para validar la comunicacion
         */
        try {
            userOptional = cursoService.deleteUserOfCourse(user, cursoId);
        } catch (FeignException error) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Error", "No se encuentra usuario por el Id o Error en la comunicacion " + error.getMessage()));
        }
        if (userOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
        }
        return ResponseEntity.notFound().build();
    }
    private ResponseEntity<Map<String, String>> validaciones(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errores.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
