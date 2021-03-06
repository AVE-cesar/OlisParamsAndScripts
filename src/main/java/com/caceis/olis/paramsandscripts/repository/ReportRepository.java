package com.caceis.olis.paramsandscripts.repository;

import com.caceis.olis.paramsandscripts.domain.Report;

import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Report entity.
 */
public interface ReportRepository extends JpaRepository<Report,Long>, ReportRepositoryCustom {

}
