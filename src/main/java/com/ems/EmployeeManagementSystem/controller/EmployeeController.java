package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.model.Employee;
import com.ems.EmployeeManagementSystem.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/profile/{id}")
    public ResponseEntity<Employee> viewProfile(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.viewEmployeeProfile(id));
    }

    @PatchMapping("/profile/edit/{id}")
    public ResponseEntity<Employee> editProfile(@PathVariable Long id, @RequestBody Employee updatedInfo) {
        return ResponseEntity.ok(employeeService.editProfile(id, updatedInfo));
    }

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        return new ResponseEntity<>(employeeService.addEmployee(employee), HttpStatus.CREATED);
    }

    @PostMapping("/{employeeId}/skills/add/{skillId}")
    public ResponseEntity<Employee> addSkill(@PathVariable Long employeeId, @PathVariable Long skillId) {
        return ResponseEntity.ok(employeeService.addSkillToEmployee(employeeId, skillId));
    }

    @PostMapping("/{employeeId}/projects/assign/{projectId}")
    public ResponseEntity<Employee> assignProject(@PathVariable Long employeeId, @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeService.assignProjectToEmployee(employeeId, projectId));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employee));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.ok("Employee deleted successfully");
    }

    @DeleteMapping("/{employeeId}/skills/remove/{skillId}")
    public ResponseEntity<Employee> removeSkill(@PathVariable Long employeeId, @PathVariable Long skillId) {
        return ResponseEntity.ok(employeeService.removeSkillFromEmployee(employeeId, skillId));
    }

    @DeleteMapping("/{employeeId}/projects/remove/{projectId}")
    public ResponseEntity<Employee> removeProject(@PathVariable Long employeeId, @PathVariable Long projectId) {
        return ResponseEntity.ok(employeeService.removeProjectFromEmployee(employeeId, projectId));
    }

}