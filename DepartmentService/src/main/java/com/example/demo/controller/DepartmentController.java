package com.example.demo.controller;

import com.example.demo.model.request.DepartmentRequest;
import com.example.demo.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;


    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getDepartment(@PathVariable("id") long departmentId) {
        DepartmentRequest department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }

    @PostMapping(value = "/saveDept", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DepartmentRequest> saveDepartment(@RequestBody DepartmentRequest department) {
        DepartmentRequest savedDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

}
