package com.emp.poc.service.impl;

import com.emp.poc.controller.EmployeeController;
import com.emp.poc.entity.Department;
import com.emp.poc.entity.Employee;
import com.emp.poc.exception.RecordNotFoundException;
import com.emp.poc.repository.DepartmentRepository;
import com.emp.poc.repository.EmployeeRepository;
import com.emp.poc.service.EmployeeService;
import com.opencsv.CSVWriter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DepartmentRepository departmentRepository;

    Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    /**
     * Fetch Employees List from the DB.
     * @return
     */
    public List<Employee> getAllEmployees() {
        List<Employee> result = (List<Employee>) employeeRepository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Employee>();
        }
    }

    /**
     * Fetch Departments List from the DB.
     * @return
     */
    public List<Department> getAllDepartments() {
        List<Department> result = (List<Department>) departmentRepository.findAll();
        if (result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Department>();
        }
    }

    /**
     * Fetch Employee by ID from DB
     * @param id
     * @return
     * @throws RecordNotFoundException
     */
    public Employee getEmpById(Integer id) throws RecordNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            return employee.get();
        } else {
            throw new RecordNotFoundException
                    ("No Employee record exist for given id: " + id);
        }
    }

    /**
     * Create Employee (OR) Update Employee by ID into DB
     * @param entity
     */
    public void createOrUpdateEmp(Employee entity) {
        if (entity.getId() == null) {
            entity = employeeRepository.save(entity);
        } else {
            Optional<Employee> employee = employeeRepository.findById(entity.getId());
            if (employee.isPresent()) {
                Employee empEntity = employee.get();
                empEntity
                        .setFirstName(entity.getFirstName());
                empEntity.setLastName(entity.getLastName());
                empEntity.setEmail(entity.getEmail());
                empEntity.setSalary(entity.getSalary());
                empEntity.setJobNm(entity.getJobNm());
                empEntity.setDepartment(entity.getDepartment());
                empEntity = employeeRepository.save(empEntity);
            }
        }
    }

    /**
     * Delete Employee by ID into DB
     * @param id
     * @throws RecordNotFoundException
     */
    public void deleteEmpById(Integer id) throws RecordNotFoundException {
        Optional<Employee> employee = employeeRepository.findById(id);
        if (employee.isPresent()) {
            employeeRepository.deleteById(id);
        } else {
            throw new RecordNotFoundException
                    ("No Employee record exist for given id");
        }
    }

    /**
     * Fetch Employees List from the DB and Paginated.
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Override
    public Page<Employee> findPaginated(int pageNo, int pageSize) {
        LOGGER.info("In EmployeeServiceImpl - to fetch Employee Details from repository");
        //PageRequest object by passing in the requested page number and the page size.
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        return employeeRepository.findAll(pageable);
    }

    /**
     * CSV File creation
     * @param employees
     * @param context
     * @return
     */
    public boolean createCsv(List<Employee> employees, ServletContext context) {
        String filePath = context.getRealPath("resources/reports");
        System.out.println("filePath > " + filePath);
        boolean exists = new File(filePath).exists();
        if (!exists) {
            new File(filePath).mkdirs();
        }
        File file = new File(filePath + "/" + File.separator + "employee.csv");
        try {
            FileWriter fileWriter = new FileWriter(file);
            CSVWriter csvWriter = new CSVWriter(fileWriter);
            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[]{"Employee Id", "Employee First Name", "Employee Last Name", "Email", "Job Name"});
            for (Employee employee : employees) {
                data.add(new String[]{employee.getId() + "", employee.getFirstName(), employee.getLastName(), employee.getEmail(), employee.getJobNm()});
            }
            csvWriter.writeAll(data);
            csvWriter.close();
            return true;

        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    /**
     * CSV File Download
     * @param fullPath
     * @param response
     * @param fileName
     */
    public void fileDownload(String fullPath, ServletContext servletContext, HttpServletResponse response, String fileName){
        File file=new File(fullPath);
        final int BUFFER_SIZE=4096;
        if(file.exists()){
            try{
                FileInputStream fileInputStream=new FileInputStream(file);
                String mimeType=servletContext.getMimeType(fullPath);
                response.setContentType(mimeType);
                response.setHeader("content-disposition","attachment: filename="+fileName);
                OutputStream outputStream=response.getOutputStream();
                byte[] buffer=new byte[BUFFER_SIZE];
                int bytesRead=-1;
                while((bytesRead=fileInputStream.read(buffer))!=-1){
                    outputStream.write(buffer,0,bytesRead);
                }
                fileInputStream.close();
                outputStream.close();
                file.delete();

            }catch (Exception e){
                System.out.println(e);
            }

        }

    }
}
