package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AGACOrganization;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AGACOrganization entity.
 */
public interface AGACOrganizationRepository extends JpaRepository<AGACOrganization,Long> {

}
