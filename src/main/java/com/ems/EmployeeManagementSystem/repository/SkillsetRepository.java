package com.ems.EmployeeManagementSystem.repository;

import com.ems.EmployeeManagementSystem.model.Skillset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SkillsetRepository extends JpaRepository<Skillset, Long> {
}
