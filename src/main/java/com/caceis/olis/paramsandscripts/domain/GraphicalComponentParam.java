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
 * PAR_CPO_PAR
 */
@Entity
@Table(name = "PAR_CPO_PAR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "graphicalcomponentparam")
public class GraphicalComponentParam implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_CPO_PAR_IDE")
    private Long id;

    @Size(max = 50)
    @Column(name = "PAR_CPO_PAR_NME", length = 50)
    private String name;

    @Size(max = 50)
    @Column(name = "PAR_CPO_PAR_VAL", length = 50)
    private String value;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAR_CPO_PAR_SVC")
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

        GraphicalComponentParam graphicalComponentParam = (GraphicalComponentParam) o;

        if ( ! Objects.equals(id, graphicalComponentParam.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GraphicalComponentParam{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", value='" + value + "'" +
            ", mode='" + mode + "'" +
            '}';
    }
}
