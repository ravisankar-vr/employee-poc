package com.emp.poc.service;

import com.emp.poc.entity.Department;
import com.emp.poc.entity.Employee;
import com.emp.poc.exception.RecordNotFoundException;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<Employee> getAllEmployees();
    List<Department> getAllDepartments();
    Employee getEmpById(Integer id) throws RecordNotFoundException;
    void createOrUpdateEmp(Employee entity);
    void deleteEmpById(Integer id) throws RecordNotFoundException;
    Page<Employee> findPaginated(int pageNo, int pageSize);
    void fileDownload(String fullPath, ServletContext servletContext, HttpServletResponse response, String fileName);
    boolean createCsv(List<Employee> employees, ServletContext context);

}
