package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Authority entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
