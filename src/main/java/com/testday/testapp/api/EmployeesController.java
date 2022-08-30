package com.testday.testapp.api;

import com.testday.testapp.model.Employee;
import com.testday.testapp.repository.EmployeeRepository;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
@Log4j2
public class EmployeesController {

    private final static String uriTemplate = "http://localhost:8080/";

    private final EmployeeRepository employeeRepository;

    @GetMapping("all")
    public List<Employee> GetEmployees() {
        return employeeRepository.fetchAll();
    }

    @PostMapping(
        value = "save",
    consumes = "application/json")
    public ResponseEntity save(@Valid @RequestBody  Employee employee) {
        log.debug(employee);
        try {
            if (employee.getId() != null) {
                return ResponseEntity.badRequest().build();
            }
            employeeRepository.save(employee);
            return ResponseEntity
                .created(URI.create(uriTemplate + employee.getId()))
                .build();
        } catch (DataIntegrityViolationException e) {
            log.error("Error with DataIntegrityViolation", e);
            return ResponseEntity.badRequest().body(e);
        } catch (DataAccessException e) {
            log.error("Error with DataAccess", e);
            return ResponseEntity.internalServerError().body(e);
        }
    }

}
