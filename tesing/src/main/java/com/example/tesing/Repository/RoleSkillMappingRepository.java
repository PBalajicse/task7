package com.example.tesing.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tesing.Entity.RoleSkillMapping;

public interface RoleSkillMappingRepository  extends JpaRepository<RoleSkillMapping, Long>{
	
	List<RoleSkillMapping> findByRoleName(String roleName);
	
	boolean existsByRoleNameAndSkillId(String roleName, Long skillId);

}
