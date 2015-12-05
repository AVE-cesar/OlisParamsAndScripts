package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AuthorizationLink;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuthorizationLink entity.
 */
public interface AuthorizationLinkRepository extends JpaRepository<AuthorizationLink,Long> {

}
