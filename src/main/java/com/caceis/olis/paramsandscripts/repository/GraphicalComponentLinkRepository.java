package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.GraphicalComponentLink;
import com.caceis.olis.paramsandscripts.domain.PromptPosition;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GraphicalComponentLink entity.
 */
public interface GraphicalComponentLinkRepository extends JpaRepository<GraphicalComponentLink,Long> {

	/**
	 * Ajout AVE B.
	 * @param id
	 * @return
	 */
	List<GraphicalComponentLink> findByPromptId(Long id);
}
