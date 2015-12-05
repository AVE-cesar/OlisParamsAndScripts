package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.GlobalParameters;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GlobalParameters entity.
 */
public interface GlobalParametersRepository extends JpaRepository<GlobalParameters,Long> {

}
