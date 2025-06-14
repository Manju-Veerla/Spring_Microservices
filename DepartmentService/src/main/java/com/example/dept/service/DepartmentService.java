package com.example.dept.service;

import com.example.dept.model.request.DepartmentRequest;
import com.example.dept.model.mapper.DepartmentMapper;
import com.example.dept.model.response.DepartmentResponse;
import org.springframework.stereotype.Component;

import com.example.dept.model.entities.Department;
import com.example.dept.repo.DepartmentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    public DepartmentResponse saveDepartment(DepartmentRequest department) {
        Department dept = departmentRepository.save(departmentMapper.departmentRequestToDepartment(department));
        return departmentMapper.departmentToDepartmentResponse(dept);
    }

    public DepartmentResponse getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).get();
        return departmentMapper.departmentToDepartmentResponse(department);
    }

    public List<DepartmentResponse> getDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(departmentMapper::departmentToDepartmentResponse)
                .toList();
    }
}
