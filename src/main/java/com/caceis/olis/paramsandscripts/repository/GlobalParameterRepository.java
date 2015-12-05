package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.GlobalParameter;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GlobalParameter entity.
 */
public interface GlobalParameterRepository extends JpaRepository<GlobalParameter,String> {

}
