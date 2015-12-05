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

/**
 * PAR_CPO
 */
@Entity
@Table(name = "PAR_CPO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "graphicalcomponent")
public class GraphicalComponent implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_CPO_IDE")
    private Long id;

    @NotNull
    @Column(name = "CPO_SCR", nullable = false)
    @Lob
    private String script;

    @Size(max = 50)
    @Column(name = "PAR_CPO_DES", length = 50)
    private String description;

    @OneToMany(mappedBy = "graphicalComponent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GraphicalComponentLink> graphicalComponentLinks = new HashSet<>();

    @OneToMany(mappedBy = "graphicalComponent")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GraphicalComponentParam> graphicalComponentParams = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GraphicalComponent graphicalComponent = (GraphicalComponent) o;

        if ( ! Objects.equals(id, graphicalComponent.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GraphicalComponent{" +
            "id=" + id +
            ", script='" + script + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
