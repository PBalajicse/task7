package com.example.tesing.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tesing.Entity.AssessmentHistory;

public interface AssessmentHistoryRepository extends JpaRepository<AssessmentHistory, Long>{
	
	List<AssessmentHistory> findByUserId(Long userId);

}
