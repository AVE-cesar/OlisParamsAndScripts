package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AuthorizationSet;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuthorizationSet entity.
 */
public interface AuthorizationSetRepository extends JpaRepository<AuthorizationSet,Long> {

}
