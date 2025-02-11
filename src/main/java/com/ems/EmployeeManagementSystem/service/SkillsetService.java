package com.ems.EmployeeManagementSystem.service;

import com.ems.EmployeeManagementSystem.exception.CustomException;
import com.ems.EmployeeManagementSystem.model.Skillset;
import com.ems.EmployeeManagementSystem.repository.SkillsetRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SkillsetService {
    @Autowired
    private SkillsetRepository skillRepo;

    public Skillset addSkill(Skillset skill) {
        log.info("Adding skill: {}", skill.getName());
        return skillRepo.save(skill);
    }

    public Skillset getSkillById(Long id) {
        return skillRepo.findById(id)
                .orElseThrow(() -> new CustomException("Skill not found with ID: " + id));
    }

    public Skillset updateSkill(Long id, Skillset updatedSkill) {
        Skillset existingSkill = getSkillById(id);
        existingSkill.setName(updatedSkill.getName());
        return skillRepo.save(existingSkill);
    }

    public void deleteSkill(Long id) {
        skillRepo.deleteById(id);
        log.info("Deleted skill with ID: {}", id);
    }

    public List<Skillset> getAllSkills() {
        return skillRepo.findAll();
    }
}
