package com.example.dept.controller;

import com.example.dept.model.request.DepartmentRequest;
import com.example.dept.model.response.DepartmentResponse;
import com.example.dept.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/department")
@AllArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;


    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getDepartment(@PathVariable("id") long departmentId) {
        DepartmentResponse department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }
    @GetMapping(value = "/all", produces = "application/json")
    public ResponseEntity<List<DepartmentResponse>> getAllDepartment() {
       List<DepartmentResponse>  departments = departmentService.getDepartments();
        return ResponseEntity.ok(departments);
    }
    @PostMapping(value = "/saveDept", consumes = "application/json", produces = "application/json")
    public ResponseEntity<DepartmentResponse> saveDepartment(@RequestBody DepartmentRequest department) {
        DepartmentResponse savedDepartment = departmentService.saveDepartment(department);
        return new ResponseEntity<>(savedDepartment, HttpStatus.CREATED);
    }

}
