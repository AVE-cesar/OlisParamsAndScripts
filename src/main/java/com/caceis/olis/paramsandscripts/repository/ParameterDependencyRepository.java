package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.ParameterDependency;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParameterDependency entity.
 */
public interface ParameterDependencyRepository extends JpaRepository<ParameterDependency,Long> {

	/**
	 * B. AVE
	 * @param id
	 * @return
	 */
	List<ParameterDependency> findByPromptFatherId(Long id);
}
