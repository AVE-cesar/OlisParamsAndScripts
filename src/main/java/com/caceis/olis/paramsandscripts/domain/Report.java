package com.caceis.olis.paramsandscripts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.caceis.olis.paramsandscripts.domain.enumeration.Specific;

import com.caceis.olis.paramsandscripts.domain.enumeration.Raw;

/**
 * Les rapports
 */
@Entity
@Table(name = "RPT_FROM_ODS")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "report")
public class Report implements Serializable {

    @Id
	@Column(name = "RPT_IDE")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "RPT_NME", length = 256, nullable = false)
    private String name;

    @Size(max = 3)
    @Column(name = "SPR_TRG_EVT_CDE", length = 3)
    private String triggerCode;

    @Size(max = 3)
    @Column(name = "RPT_TYP", length = 3)
    private String type;

    @Size(max = 3)
    @Column(name = "RPT_DOM_CDE", length = 3)
    private String domain;

    @Column(name = "GEN_TYP")
    private Integer generationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "GP2_EXT")
    private Specific specific;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "RAW_RPT", nullable = false)
    private Raw raw;

    @Column(name = "FAT_RAW_RPT")
    private Long fatRawReport;

    @Size(max = 10)
    @Column(name = "USR_ACCESS_SRV", length = 10)
    private String userAccess;

    @OneToMany(mappedBy = "report")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PromptPosition> promptPositions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTriggerCode() {
        return triggerCode;
    }

    public void setTriggerCode(String triggerCode) {
        this.triggerCode = triggerCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public Integer getGenerationType() {
        return generationType;
    }

    public void setGenerationType(Integer generationType) {
        this.generationType = generationType;
    }

    public Specific getSpecific() {
        return specific;
    }

    public void setSpecific(Specific specific) {
        this.specific = specific;
    }

    public Raw getRaw() {
        return raw;
    }

    public void setRaw(Raw raw) {
        this.raw = raw;
    }

    public Long getFatRawReport() {
        return fatRawReport;
    }

    public void setFatRawReport(Long fatRawReport) {
        this.fatRawReport = fatRawReport;
    }

    public String getUserAccess() {
        return userAccess;
    }

    public void setUserAccess(String userAccess) {
        this.userAccess = userAccess;
    }

    public Set<PromptPosition> getPromptPositions() {
        return promptPositions;
    }

    public void setPromptPositions(Set<PromptPosition> promptPositions) {
        this.promptPositions = promptPositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Report report = (Report) o;

        if ( ! Objects.equals(id, report.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Report{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", triggerCode='" + triggerCode + "'" +
            ", type='" + type + "'" +
            ", domain='" + domain + "'" +
            ", generationType='" + generationType + "'" +
            ", specific='" + specific + "'" +
            ", raw='" + raw + "'" +
            ", fatRawReport='" + fatRawReport + "'" +
            ", userAccess='" + userAccess + "'" +
            '}';
    }
}
