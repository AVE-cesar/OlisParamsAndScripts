package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AuthorizationSetLink;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AuthorizationSetLink entity.
 */
public interface AuthorizationSetLinkRepository extends JpaRepository<AuthorizationSetLink,Long> {

}
