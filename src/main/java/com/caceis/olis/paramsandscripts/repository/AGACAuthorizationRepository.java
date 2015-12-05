package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AGACAuthorization;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AGACAuthorization entity.
 */
public interface AGACAuthorizationRepository extends JpaRepository<AGACAuthorization,Long> {

}
