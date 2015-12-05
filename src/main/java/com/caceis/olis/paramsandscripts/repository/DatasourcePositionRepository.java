package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.DatasourcePosition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DatasourcePosition entity.
 */
public interface DatasourcePositionRepository extends JpaRepository<DatasourcePosition,Long> {

	/**
	 * B. AVE
	 * @param id
	 * @return
	 */
	List<DatasourcePosition> findByPromptId(Long id);

}
