package com.example.ADPProject.api;

import com.example.ADPProject.model.Customer;
import com.example.ADPProject.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/account")
public class RegisterAPI {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/register") 
    public ResponseEntity<Void> registerCustomer(@RequestBody Customer newCustomer) {
        if (newCustomer.getId() != null || newCustomer.getName() == null || newCustomer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        // Guardar el nuevo cliente en la base de datos
        newCustomer = customerRepository.save(newCustomer); // Guardar el cliente

        // Crear la URI del nuevo recurso
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCustomer.getId())
                .toUri();

        return ResponseEntity.created(location).build(); // Retornar 201 Created
    }

    @GetMapping("/register")
    public ResponseEntity<String> showRegistrationForm() {
        // Aquí podrías retornar un formulario HTML o un mensaje
        return ResponseEntity.ok("Aquí va el formulario de registro."); // Solo como ejemplo
    }
}
