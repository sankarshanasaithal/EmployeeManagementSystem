package com.ems.EmployeeManagementSystem.controller;

import com.ems.EmployeeManagementSystem.model.Skillset;
import com.ems.EmployeeManagementSystem.service.SkillsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
public class SkillsetController {
    @Autowired
    private SkillsetService skillService;

    @GetMapping("/{id}")
    public ResponseEntity<Skillset> getSkill(@PathVariable Long id) {
        return ResponseEntity.ok(skillService.getSkillById(id));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Skillset>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @PostMapping("/add")
    public ResponseEntity<Skillset> addSkill(@RequestBody Skillset skill) {
        return new ResponseEntity<>(skillService.addSkill(skill), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Skillset> updateSkill(@PathVariable Long id, @RequestBody Skillset skill) {
        return ResponseEntity.ok(skillService.updateSkill(id, skill));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteSkill(@PathVariable Long id) {
        skillService.deleteSkill(id);
        return ResponseEntity.ok("Skill deleted successfully");
    }

}
