package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.GraphicalComponent;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GraphicalComponent entity.
 */
public interface GraphicalComponentRepository extends JpaRepository<GraphicalComponent,Long> {

}
