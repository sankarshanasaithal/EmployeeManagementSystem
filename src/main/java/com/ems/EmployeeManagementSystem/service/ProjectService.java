package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.exception.CustomException;
import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.model.Project;
import com.ems.EmployeeManagementSystem.repository.EmployeeRepository;
import com.ems.EmployeeManagementSystem.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepo;

    @Autowired
    private EmployeeRepository employeeRepo;

    public Project addProject(Project project) {
        log.info("Adding project: {}", project.getProjectName());
        return projectRepo.save(project);
    }

    public Project getProjectById(Long id) {
        return projectRepo.findById(id)
                .orElseThrow(() -> new CustomException("Project not found with ID: " + id));
    }

    public Project updateProject(Long id, Project updatedProject) {
        Project existingProject = getProjectById(id);
        existingProject.setProjectName(updatedProject.getProjectName());
        existingProject.setDescription(updatedProject.getDescription());
        return projectRepo.save(existingProject);
    }

    public void deleteProject(Long id) {
        projectRepo.deleteById(id);
        log.info("Deleted project with ID: {}", id);
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    // Assign Employees to a Project
    public Project assignEmployeesToProject(Long projectId, Set<Long> employeeIds) {
        Project project = getProjectById(projectId);
        Set<Employee> employees = Set.copyOf(employeeRepo.findAllById(employeeIds));
        project.setEmployees(employees);
        log.info("Assigned employees to project: {}", project.getProjectName());
        return projectRepo.save(project);
    }
}
