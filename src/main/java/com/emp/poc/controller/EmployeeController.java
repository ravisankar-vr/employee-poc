package com.emp.poc.controller;

import com.emp.poc.entity.Department;
import com.emp.poc.entity.Employee;
import com.emp.poc.exception.RecordNotFoundException;
import com.emp.poc.service.EmployeeService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private ServletContext servletContext;

    Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    private static final int PAGE_SIZE = 5;
    private static final int PAGE_NO = 1;

    /**
     * List Employees in home page with Pagination.
     *
     * @param model
     * @return
     */
    /*@GetMapping("/")
    public String getAllEmployeeView(Model model) {
        //Default Page No ie., 1
        return findPaginated(model, PAGE_NO);
    }*/

    /**
     * Display Employee Form to Create a new employee
     *
     * @param model
     * @return
     */
    @GetMapping("/showNewEmployeeForm")
    public String showNewEmployeeForm(Model model) {
        // Create model attribute to bind form data
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    /**
     * Create (OR) Update Employee
     *
     * @param employee
     * @param result
     * @return
     */
    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute("employee") @Valid Employee employee, BindingResult result) {
        if (result.hasErrors()) {
            // If there are validation errors, return to the form
            return "new_employee";
        }
        employeeService.createOrUpdateEmp(employee);
        return "redirect:/v1/employees/";
    }

    /**
     * Display Employee Form to Update an employee
     *
     * @param model
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(Model model, @PathVariable("id") Integer id)
            throws RecordNotFoundException {
        //Get Employee from the service by EmpId
        Employee employee = employeeService.getEmpById(id);
        //Set Employee as a model attribute to pre-populate the form
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    /**
     * Delete an Employee
     *
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    @GetMapping("/deleteEmployee/{id}")
    public String deleteEmployee(@PathVariable("id") Integer id)
            throws RecordNotFoundException {
        employeeService.deleteEmpById(id);
        return "redirect:/v1/employees/";
    }


    /*@GetMapping("/page/{pageNo}")
    public String findPaginated(Model model, @PathVariable("pageNo") int pageNo) {
        Page<Employee> page = employeeService.findPaginated(pageNo, PAGE_SIZE);
        List<Employee> listEmployee = page.getContent();
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("listemployees", listEmployee);
        return "index";
    }*/

    /*@GetMapping("/employees-list/{pageNo}/{pageSize}")
    public Page<Employee> getAllEmployees(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        return employeeService.findPaginated(pageNo, pageSize);
    }*/

    @GetMapping("/employees-list/{pageNo}/{pageSize}")
    public List<Employee> getAllEmployees(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        LOGGER.info("In EmployeeService RestController - to fetch Employee Details ");
        Page<Employee> recordsPage = employeeService.findPaginated(pageNo, pageSize);
        return recordsPage.getContent();
    }

    /*@GetMapping("/employees-list/{pageNo}/{pageSize}")
    public ResponseEntity<Page<Employee>> getAllEmployees(@PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) {
        Page<Employee> empPage= employeeService.findPaginated(pageNo, pageSize);

        return ResponseEntity.ok().body(empPage);
    }*/

    /**
     * For display Deparments data in drop-down control, binding data into Model
     *
     * @return
     */
    @ModelAttribute("departments")
    public List<Department> getAllDepartments() {
        return employeeService.getAllDepartments();
    }

    /**
     * Trigger for CSV file Generation
     *
     * @param request
     * @param response
     */
    @GetMapping(value = "/createCsv", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createCsv(HttpServletRequest request, HttpServletResponse response) {
        List<Employee> employees = employeeService.getAllEmployees();
        boolean isFlag = employeeService.createCsv(employees, servletContext);
        if (isFlag) {
            String fullPath = request.getServletContext().getRealPath("/resources/reports/" + "employee.csv");
            employeeService.fileDownload(fullPath, servletContext, response, "employee.csv");
        }

    }


}
