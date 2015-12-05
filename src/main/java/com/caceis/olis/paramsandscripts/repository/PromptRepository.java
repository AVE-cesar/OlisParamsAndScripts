package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.Prompt;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Prompt entity.
 */
public interface PromptRepository extends JpaRepository<Prompt,Long> {

}
