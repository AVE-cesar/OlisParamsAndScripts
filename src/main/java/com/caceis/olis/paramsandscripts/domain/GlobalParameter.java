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

/**
 * A GlobalParameter.
 */
@Entity
@Table(name = "GLO_PAR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "globalparameter")
public class GlobalParameter implements Serializable {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_CAT_IDE")
    private String id;

    @NotNull
    @Size(max = 50)
    @Column(name = "KEY", length = 50, nullable = false)
    private String key;

    @NotNull
    @Size(max = 4000)
    @Column(name = "VAL", length = 4000, nullable = false)
    private String value;

    @Size(max = 200)
    @Column(name = "DESCR", length = 200)
    private String description;
/*
    @ManyToOne
    @JoinColumn(name="PAR_CAT_IDE")*/
    private ParamCategory category;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ParamCategory getCategory() {
        return category;
    }

    public void setCategory(ParamCategory paramCategory) {
        this.category = paramCategory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GlobalParameter globalParameter = (GlobalParameter) o;

        if ( ! Objects.equals(id, globalParameter.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GlobalParameter{" +
            "id=" + id +
            ", key='" + key + "'" +
            ", value='" + value + "'" +
            ", description='" + description + "'" +
            '}';
    }
}
