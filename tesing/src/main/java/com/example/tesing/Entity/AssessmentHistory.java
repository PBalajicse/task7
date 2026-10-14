package com.example.tesing.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "assessment_history")
public class AssessmentHistory {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String targetRole;
    private double matchedPercentage; 
    private LocalDateTime completedAt = LocalDateTime.now();

    // Getters and Setters
    public Long getId() 
    { return id; 
    }
    public void setId(Long id) { 
    	this.id = id; 
    	}
    public User getUser() { 
    	return user; 
    	}
    public void setUser(User user) { this.user = user; }
    public String getTargetRole() { return targetRole; }
    public void setTargetRole(String targetRole) { this.targetRole = targetRole; }
    public double getMatchedPercentage() { return matchedPercentage; }
    public void setMatchedPercentage(double matchedPercentage) { this.matchedPercentage = matchedPercentage; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

}
