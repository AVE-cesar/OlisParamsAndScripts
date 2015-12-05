package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.PromptPosition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PromptPosition entity.
 */
public interface PromptPositionRepository extends JpaRepository<PromptPosition,Long> {

	List<PromptPosition> findByReportId(Long id);
}
