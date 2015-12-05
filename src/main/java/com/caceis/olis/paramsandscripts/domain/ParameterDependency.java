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

import com.caceis.olis.paramsandscripts.domain.enumeration.DependencyType;

/**
 * PAR_LNK
 */
@Entity
@Table(name = "PAR_LNK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "parameterdependency")
public class ParameterDependency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_LNK_IDE")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "LNK_TYP", nullable = false)
    private DependencyType type;

    @Size(max = 3)
    @Column(name = "CHK_OPE", length = 3)
    private String checkOperation;

    @Column(name = "CHK_SCR")
    @Lob
    private String script;

    @ManyToOne
    @JoinColumn(name="FST_PAR_IDE")
    private Prompt promptFather;

    @ManyToOne
    @JoinColumn(name="SDY_PAR_IDE")
    private Prompt promptChild;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DependencyType getType() {
        return type;
    }

    public void setType(DependencyType type) {
        this.type = type;
    }

    public String getCheckOperation() {
        return checkOperation;
    }

    public void setCheckOperation(String checkOperation) {
        this.checkOperation = checkOperation;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public Prompt getPromptFather() {
        return promptFather;
    }

    public void setPromptFather(Prompt prompt) {
        this.promptFather = prompt;
    }

    public Prompt getPromptChild() {
        return promptChild;
    }

    public void setPromptChild(Prompt prompt) {
        this.promptChild = prompt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParameterDependency parameterDependency = (ParameterDependency) o;

        if ( ! Objects.equals(id, parameterDependency.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParameterDependency{" +
            "id=" + id +
            ", type='" + type + "'" +
            ", checkOperation='" + checkOperation + "'" +
            ", script='" + script + "'" +
            '}';
    }
}
