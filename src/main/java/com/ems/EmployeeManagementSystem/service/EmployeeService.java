package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.exception.CustomException;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.Project;
import com.ems.EmployeeManagementSystem.model.Skillset;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepository;
import com.ems.EmployeeManagementSystem.repository.ProjectRepository;
import com.ems.EmployeeManagementSystem.repository.SkillsetRepository;
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

    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private SkillsetRepository skillRepo;

    public Employee addEmployee(Employee employee) {
        log.info("Adding employee: {}", employee.getName());
        return employeeRepo.save(employee);
    }

    public Employee viewProfile(Long id) {
        return getEmployeeById(id);
    }

//    @Cacheable(value = "employees", key = "#id")
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

    // Edit Specific Profile Information
    @CachePut(value = "employees", key = "#id")
    public Employee editProfile(Long id, Employee updatedInfo) {
        Employee existing = getEmployeeById(id);

        if (updatedInfo.getName() != null) existing.setName(updatedInfo.getName());
        if (updatedInfo.getEmail() != null) existing.setEmail(updatedInfo.getEmail());
        if (updatedInfo.getRole() != null) existing.setRole(updatedInfo.getRole());
        if (updatedInfo.getDepartment() != null) existing.setDepartment(updatedInfo.getDepartment());
        if (updatedInfo.getSkills() != null) existing.setSkills(updatedInfo.getSkills());
        if (updatedInfo.getProjects() != null) existing.setProjects(updatedInfo.getProjects());

        return employeeRepo.save(existing);
    }

    @CachePut(value = "employees", key = "#employeeId")
    public Employee addSkillToEmployee(Long employeeId, Long skillId) {
        Employee employee = getEmployeeById(employeeId);
        Skillset skill = skillRepo.findById(skillId)
                .orElseThrow(() -> new CustomException("Skill not found with ID: " + skillId));

        employee.getSkills().add(skill);
        return employeeRepo.save(employee);
    }

    @CachePut(value = "employees", key = "#employeeId")
    public Employee removeSkillFromEmployee(Long employeeId, Long skillId) {
        Employee employee = getEmployeeById(employeeId);
        Skillset skill = skillRepo.findById(skillId)
                .orElseThrow(() -> new CustomException("Skill not found with ID: " + skillId));

        employee.getSkills().remove(skill);
        return employeeRepo.save(employee);
    }

    @CachePut(value = "employees", key = "#employeeId")
    public Employee assignProjectToEmployee(Long employeeId, Long projectId) {
        Employee employee = getEmployeeById(employeeId);
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found with ID: " + projectId));

        employee.getProjects().add(project);
        return employeeRepo.save(employee);
    }

    @CachePut(value = "employees", key = "#employeeId")
    public Employee removeProjectFromEmployee(Long employeeId, Long projectId) {
        Employee employee = getEmployeeById(employeeId);
        Project project = projectRepo.findById(projectId)
                .orElseThrow(() -> new CustomException("Project not found with ID: " + projectId));

        employee.getProjects().remove(project);
        return employeeRepo.save(employee);
    }

    // View Full Employee Profile
    @Cacheable(value = "employees", key = "#id")
    public Employee viewEmployeeProfile(Long id) {
        return employeeRepo.findById(id)
                .orElseThrow(() -> new CustomException("Employee not found with ID: " + id));
    }

    @CachePut(value = "employees", key = "#id")
    public Employee editEmployeeProfile(Long id, Employee updatedProfile) {
        Employee existing = viewEmployeeProfile(id);
        existing.setName(updatedProfile.getName());
        existing.setEmail(updatedProfile.getEmail());
        existing.setRole(updatedProfile.getRole());
        existing.setDepartment(updatedProfile.getDepartment());
        existing.setSkills(updatedProfile.getSkills());
        existing.setProjects(updatedProfile.getProjects());
        return employeeRepo.save(existing);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepo.findAll();
    }
}