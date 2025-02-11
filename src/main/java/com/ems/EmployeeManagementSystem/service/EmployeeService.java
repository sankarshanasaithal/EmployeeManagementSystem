package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.exception.CustomException;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepo;

    public Employee addEmployee(Employee employee) {
        log.info("Adding employee: {}", employee.getName());
        return employeeRepo.save(employee);
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee getEmployeeById(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new CustomException("Employee not found with ID: " + id));
    }

    @CachePut(value = "employees", key = "#id")
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = getEmployeeById(id);
        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setRole(updatedEmployee.getRole());
        existing.setDepartment(updatedEmployee.getDepartment());
        return employeeRepo.save(existing);
    }

    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id) {
        employeeRepo.deleteById(id);
        log.info("Deleted employee with ID: {}", id);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
}