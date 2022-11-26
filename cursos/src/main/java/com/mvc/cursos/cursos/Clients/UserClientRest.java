package com.mvc.cursos.cursos.Clients;

import com.mvc.cursos.cursos.Models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "micorservicio-user", url = "localhost:8001")
public interface UserClientRest {

    /**
     * Agregar os metodos del controlador con sus anotaciones sin validaciones
     */
    @GetMapping("/{id}")
    User detailUser(@PathVariable long id);

    @PostMapping
    User createUser(@RequestBody User user);


}
