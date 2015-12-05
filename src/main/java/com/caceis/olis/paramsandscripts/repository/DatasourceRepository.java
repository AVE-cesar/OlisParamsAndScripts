package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.Datasource;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Datasource entity.
 */
public interface DatasourceRepository extends JpaRepository<Datasource,Long> {

}
