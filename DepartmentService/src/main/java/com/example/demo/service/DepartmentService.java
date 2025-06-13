package com.example.demo.service;

import com.example.demo.model.request.DepartmentRequest;
import com.example.demo.model.mapper.DepartmentMapper;
import org.springframework.stereotype.Component;

import com.example.demo.model.entities.Department;
import com.example.demo.repo.DepartmentRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@AllArgsConstructor
@Slf4j
public class DepartmentService {

    private DepartmentRepository departmentRepository;
    private DepartmentMapper departmentMapper;

    public DepartmentRequest saveDepartment(DepartmentRequest department) {
        Department dept = departmentRepository.save(departmentMapper.departmentDtoToDepartment(department));
        return departmentMapper.departmentToDepartmentDto(dept);
    }

    public DepartmentRequest getDepartmentById(Long departmentId) {
        Department department = departmentRepository.findById(departmentId).get();
        return departmentMapper.departmentToDepartmentDto(department);
    }
}
