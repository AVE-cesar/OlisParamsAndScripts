package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.CheckScript;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CheckScript entity.
 */
public interface CheckScriptRepository extends JpaRepository<CheckScript,Long> {

}
