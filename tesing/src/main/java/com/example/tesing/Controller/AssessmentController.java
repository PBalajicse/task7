package com.example.tesing.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tesing.Entity.AssessmentHistory;
import com.example.tesing.Entity.RoleSkillMapping;
import com.example.tesing.Entity.User;
import com.example.tesing.Repository.AssessmentHistoryRepository;
import com.example.tesing.Repository.RoleSkillMappingRepository;
import com.example.tesing.Repository.UserRepository;

public class AssessmentController {

	 @Autowired
	    private RoleSkillMappingRepository mappingRepository;

	    @Autowired
	    private AssessmentHistoryRepository historyRepository;

	    @Autowired
	    private UserRepository userRepository;
	    @GetMapping("/")
	    public String homePage() {
	        return "index"; 
	    }

	    @PostMapping("/analyze")
	    public ResponseEntity<Map<String, Object>> runGapAnalysis(
	            @RequestParam Long userId,
	            @RequestParam String targetRole,
	            @RequestBody List<Long> studentSkillIds) {

	        
	        List<RoleSkillMapping> requiredSkills = mappingRepository.findByRoleName(targetRole);
	        Map<String, Object> response = new HashMap<>();

	        if (requiredSkills.isEmpty()) {
	            response.put("error", "Role not configured by admin");
	            return ResponseEntity.badRequest().body(response);
	        }

	        double totalWeight = 0.0;
	        double studentScore = 0.0;
	        
	        List<String> missingSkills = new ArrayList<>();

	        // 2. மாணவர் பெற்ற மதிப்பெண் மற்றும் விடுபட்ட திறன்களைக் கணக்கிடுதல்
	        for (RoleSkillMapping mapping : requiredSkills) {
	            totalWeight += mapping.getWeight();
	            if (studentSkillIds.contains(mapping.getSkill().getId())) {
	                studentScore += mapping.getWeight(); // திறன் இருந்தால் மதிப்பெண் கூடும்
	            } else {
	                missingSkills.add(mapping.getSkill().getName()); // TC12: விடுபட்ட திறன்
	            }
	        }

	        // 3. சதவீத கணக்கீடு (TC09, TC10, TC11)
	        double matchPercentage = (studentScore / totalWeight) * 100;

	        // 4. வரலாற்றுப் பதிவில் சேமித்தல் (TC14)
	        User student = userRepository.findById(userId).orElse(null);
	        if (student != null) {
	            AssessmentHistory history = new AssessmentHistory();
	            history.setUser(student);
	            history.setTargetRole(targetRole);
	            history.setMatchedPercentage(matchPercentage);
	            historyRepository.save(history);
	        }

	        // 5. TC13: விடுபட்ட திறன்களுக்கு ஏற்றவாறு பரிந்துரைகள் வழங்குதல்
	        List<String> recommendations = new ArrayList<>();
	        for (String skill : missingSkills) {
	            recommendations.add("Please complete Udemy or Coursera certification for " + skill);
	        }

	        // அவுட்புட் முடிவு
	        response.put("targetRole", targetRole);
	        response.put("matchPercentage", matchPercentage);
	        response.put("missingSkills", missingSkills);
	        response.put("recommendations", recommendations);

	        return ResponseEntity.ok(response);
	    }
	
}
