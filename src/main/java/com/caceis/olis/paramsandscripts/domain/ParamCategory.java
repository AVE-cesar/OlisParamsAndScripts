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
 * les prompts
 */
@Entity
@Table(name = "PAR_CAT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "paramcategory")
public class ParamCategory implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_CAT_IDE")
    private String id;

    @Size(max = 255)
    @Column(name = "PAR_CAT_DES", length = 255)
    private String description;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<GlobalParameter> Parameterss = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<GlobalParameter> getParameterss() {
        return Parameterss;
    }

    public void setParameterss(Set<GlobalParameter> globalParameters) {
        this.Parameterss = globalParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ParamCategory paramCategory = (ParamCategory) o;

        if ( ! Objects.equals(id, paramCategory.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ParamCategory{" +
            "id=" + id +
            ", description='" + description + "'" +
            '}';
    }
}
