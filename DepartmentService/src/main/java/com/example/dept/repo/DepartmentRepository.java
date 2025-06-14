package com.example.dept.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.dept.model.entities.Department;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
