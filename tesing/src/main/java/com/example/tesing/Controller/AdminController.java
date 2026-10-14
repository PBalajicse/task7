package com.example.tesing.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tesing.Entity.RoleSkillMapping;
import com.example.tesing.Entity.Skill;
import com.example.tesing.Repository.RoleSkillMappingRepository;
import com.example.tesing.Repository.SkillRepository;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
	
    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private RoleSkillMappingRepository mappingRepository;

    // 1. CREATE NEW SKILL (Handles TC15)
    @PostMapping("/skills")
    public ResponseEntity<Map<String, String>> createSkill(@RequestBody Skill skill) {
        Map<String, String> response = new HashMap<>();
        
        // Check if skill already exists
        if (skillRepository.findByName(skill.getName()).isPresent()) {
            response.put("error", "Skill already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // TC15: Admin creates skill -> Skill available
        skillRepository.save(skill);
        response.put("message", "Skill created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 2. MAP SKILL TO ROLE WITH WEIGHT (Handles TC16, TC19)
    @PostMapping("/mappings")
    public ResponseEntity<Map<String, String>> mapSkillToRole(@RequestBody 
    		RoleSkillMapping mapping) {
        Map<String, String> response = new HashMap<>();

        // TC19: Duplicate mapping check (ஒரே ரோலுக்கு ஒரே ஸ்கில் மீண்டும் வராமல் தடுத்தல்)
        if (mappingRepository.existsByRoleNameAndSkillId(mapping.getRoleName(), 
        		mapping.getSkill().getId())) {
            response.put("error", "Rejected: Duplicate mapping");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // TC16: Admin mapping -> Map skill to role -> Weight saved
        mappingRepository.save(mapping);
        response.put("message", "Weight saved successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 3. GET ALL SKILLS LIST (Handles TC08)
    @GetMapping("/skills")
    public ResponseEntity<List<Skill>> getAllSkills() {
        // TC08: Open assessment -> Skills shown
        return ResponseEntity.ok(skillRepository.findAll());
    

}
}
