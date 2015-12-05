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

import com.caceis.olis.paramsandscripts.domain.enumeration.PromptType;

/**
 * A Prompt.
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "prompt")
public class Prompt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RPT_PAR_IDE")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "RPT_PAR_NME", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "PAR_SYS_NME", length = 255, nullable = false)
    private String systemName;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PAR_TYP", nullable = false)
    private PromptType type;
	
    @Lob
    @Column(name = "PAR_TRF_SCR")
    private String transformationScript;

    @Size(max = 50)
    @Column(name = "PAR_TTL", length = 50)
    private String visibleName;

    @Lob
    @Column(name = "PAR_DFL_VAL")
    private String defaultValueScript;

    @NotNull
    @Column(name = "SOR_ORD", nullable = false)
    private Long order;

    @OneToMany(mappedBy = "prompt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PromptPosition> promptPositions = new HashSet<>();

    @OneToMany(mappedBy = "prompt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasourcePosition> datasourcePositions = new HashSet<>();

    @OneToMany(mappedBy = "prompt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GraphicalComponentLink> graphicalComponentLinks = new HashSet<>();

    @OneToMany(mappedBy = "prompt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GraphicalComponentParam> graphicalComponentParams = new HashSet<>();

    @OneToMany(mappedBy = "prompt")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CheckScript> checkScripts = new HashSet<>();

    @OneToMany(mappedBy = "promptFather")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ParameterDependency> ParameterDependencyFathers = new HashSet<>();

    @OneToMany(mappedBy = "promptChild")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ParameterDependency> ParameterDependencyChilds = new HashSet<>();

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

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public PromptType getType() {
        return type;
    }

    public void setType(PromptType type) {
        this.type = type;
    }

    public String getTransformationScript() {
        return transformationScript;
    }

    public void setTransformationScript(String transformationScript) {
        this.transformationScript = transformationScript;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public String getDefaultValueScript() {
        return defaultValueScript;
    }

    public void setDefaultValueScript(String defaultValueScript) {
        this.defaultValueScript = defaultValueScript;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Set<PromptPosition> getPromptPositions() {
        return promptPositions;
    }

    public void setPromptPositions(Set<PromptPosition> promptPositions) {
        this.promptPositions = promptPositions;
    }

    public Set<DatasourcePosition> getDatasourcePositions() {
        return datasourcePositions;
    }

    public void setDatasourcePositions(Set<DatasourcePosition> datasourcePositions) {
        this.datasourcePositions = datasourcePositions;
    }

    public Set<GraphicalComponentLink> getGraphicalComponentLinks() {
        return graphicalComponentLinks;
    }

    public void setGraphicalComponentLinks(Set<GraphicalComponentLink> graphicalComponentLinks) {
        this.graphicalComponentLinks = graphicalComponentLinks;
    }

    public Set<GraphicalComponentParam> getGraphicalComponentParams() {
        return graphicalComponentParams;
    }

    public void setGraphicalComponentParams(Set<GraphicalComponentParam> graphicalComponentParams) {
        this.graphicalComponentParams = graphicalComponentParams;
    }

    public Set<CheckScript> getCheckScripts() {
        return checkScripts;
    }

    public void setCheckScripts(Set<CheckScript> checkScripts) {
        this.checkScripts = checkScripts;
    }

    public Set<ParameterDependency> getParameterDependencyFathers() {
        return ParameterDependencyFathers;
    }

    public void setParameterDependencyFathers(Set<ParameterDependency> parameterDependencys) {
        this.ParameterDependencyFathers = parameterDependencys;
    }

    public Set<ParameterDependency> getParameterDependencyChilds() {
        return ParameterDependencyChilds;
    }

    public void setParameterDependencyChilds(Set<ParameterDependency> parameterDependencys) {
        this.ParameterDependencyChilds = parameterDependencys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Prompt prompt = (Prompt) o;

        if ( ! Objects.equals(id, prompt.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Prompt{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", systemName='" + systemName + "'" +
            ", type='" + type + "'" +
            ", transformationScript='" + transformationScript + "'" +
            ", visibleName='" + visibleName + "'" +
            ", defaultValueScript='" + defaultValueScript + "'" +
            ", order='" + order + "'" +
            '}';
    }
}
