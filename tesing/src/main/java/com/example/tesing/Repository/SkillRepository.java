package com.example.tesing.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.tesing.Entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long>{
	
	Optional<Skill> findByName(String name);

}
