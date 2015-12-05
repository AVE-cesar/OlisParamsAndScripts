package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.AGACUser;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AGACUser entity.
 */
public interface AGACUserRepository extends JpaRepository<AGACUser,Long> {

}
