package com.example.tesing.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
@Entity
@Table(
    name = "role_skill_mappings", 
    uniqueConstraints = {@UniqueConstraint(columnNames = {"role_name", "skill_id"})}
)
public class RoleSkillMapping {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(name = "role_name", nullable = false)
    private String roleName; 

    
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;

    
    @Column(nullable = false)
    private double weight; 

    // Getters and Setters
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRoleName() { return roleName; }
    public void setRoleName(String roleName) { this.roleName = roleName; }
    public Skill getSkill() { return skill; }
    public void setSkill(Skill skill) { this.skill = skill; }
    public double getWeight() { return weight; }
    public void setWeight(double weight) { this.weight = weight; }

}
