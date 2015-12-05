package com.caceis.olis.paramsandscripts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.caceis.olis.paramsandscripts.domain.enumeration.Mode;

/**
 * PAR_CPO_LNK
 */
@Entity
@Table(name = "PAR_CPO_LNK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "graphicalcomponentlink")
public class GraphicalComponentLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_CPO_LNK_IDE")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "PAR_CPO_LNK_SVC", nullable = false)
    private Mode mode;

    @ManyToOne
    @JoinColumn(name="RPT_PAR_IDE")
    private Prompt prompt;

    @ManyToOne
    @JoinColumn(name="PAR_CPO_IDE")
    private GraphicalComponent graphicalComponent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public GraphicalComponent getGraphicalComponent() {
        return graphicalComponent;
    }

    public void setGraphicalComponent(GraphicalComponent graphicalComponent) {
        this.graphicalComponent = graphicalComponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GraphicalComponentLink graphicalComponentLink = (GraphicalComponentLink) o;

        if ( ! Objects.equals(id, graphicalComponentLink.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GraphicalComponentLink{" +
            "id=" + id +
            ", mode='" + mode + "'" +
            '}';
    }
}
