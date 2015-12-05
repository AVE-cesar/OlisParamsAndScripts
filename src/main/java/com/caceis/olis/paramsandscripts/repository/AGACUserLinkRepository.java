package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AGACUserLink;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AGACUserLink entity.
 */
public interface AGACUserLinkRepository extends JpaRepository<AGACUserLink,Long> {

}
