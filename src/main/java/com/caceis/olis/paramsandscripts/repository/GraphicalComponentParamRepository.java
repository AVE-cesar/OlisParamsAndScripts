package com.caceis.olis.paramsandscripts.repository;

import java.util.List;

import com.caceis.olis.paramsandscripts.domain.GraphicalComponentParam;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the GraphicalComponentParam entity.
 */
public interface GraphicalComponentParamRepository extends JpaRepository<GraphicalComponentParam,Long> {

	/**
	 * B. AVE
	 * @param id
	 * @return
	 */
	List<GraphicalComponentParam> findByPromptId(Long id);

}
