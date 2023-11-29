package com.emp.poc.repository;

import com.emp.poc.entity.Department;
import com.emp.poc.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
