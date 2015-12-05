package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.ParamCategory;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParamCategory entity.
 */
public interface ParamCategoryRepository extends JpaRepository<ParamCategory,String> {

}
