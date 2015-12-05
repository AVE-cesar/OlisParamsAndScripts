package com.caceis.olis.paramsandscripts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * les prompts
 */
@Entity
@Table(name = "GLO_PAR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "globalparameters")
public class GlobalParameters implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "RPT_PAR_NME", length = 50, nullable = false)
    private String name;

    @NotNull
    @Size(max = 255)
    @Column(name = "PAR_SYS_NME", length = 255, nullable = false)
    private String technicalName;

    @NotNull
    @Size(max = 3)
    @Column(name = "PAR_TYP", length = 3, nullable = false)
    private String type;

    @Column(name = "PAR_TRF_SCR")
    @Lob
    private String script;

    @Size(max = 50)
    @Column(name = "PAR_TTL", length = 50)
    private String ttl;

    @Column(name = "PAR_DFL_VAL")
    private String defaultValue;

    @NotNull
    @Column(name = "SOR_ORD", nullable = false)
    private Integer order;

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

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GlobalParameters globalParameters = (GlobalParameters) o;

        if ( ! Objects.equals(id, globalParameters.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "GlobalParameters{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", technicalName='" + technicalName + "'" +
            ", type='" + type + "'" +
            ", script='" + script + "'" +
            ", ttl='" + ttl + "'" +
            ", defaultValue='" + defaultValue + "'" +
            ", order='" + order + "'" +
            '}';
    }
}
