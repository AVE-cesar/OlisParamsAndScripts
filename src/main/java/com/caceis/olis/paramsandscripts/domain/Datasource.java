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

import com.caceis.olis.paramsandscripts.domain.enumeration.DatasourceType;

/**
 * PAR_DTA_LST
 */
@Entity
@Table(name = "PAR_DTA_LST")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "datasource")
public class Datasource implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_DTA_LST_IDE")
    private Long id;

    @Column(name = "PAR_DTA_LST_CND")
    @Lob
    private String condition;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAR_DTA_LST_TYP")
    private DatasourceType type;

    @Column(name = "PAR_DTA_LST_SIM_VAL")
    @Lob
    private String value;

    @Size(max = 50)
    @Column(name = "PAR_DTA_LST_LOV_CDE", length = 50)
    private String code;

    @Size(max = 3)
    @Column(name = "PAR_DTA_LST_SRC", length = 3)
    private String datasourceLink;

    @Column(name = "PAR_DTA_LST_SQL")
    @Lob
    private String sql;

    @Column(name = "PAR_DTA_LST_SCR")
    @Lob
    private String script;

    @Size(max = 50)
    @Column(name = "PAR_DTA_LST_DES", length = 50)
    private String description;

    @Size(max = 1000)
    @Column(name = "PAR_DTA_LST_WEB_URL", length = 1000)
    private String url;

    @Column(name = "PAR_DTA_LST_WEB_TXT")
    @Lob
    private String request;

    @Column(name = "PAR_DTA_LST_WEB_PTH")
    @Lob
    private String response;

    @OneToMany(mappedBy = "datasource")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<DatasourcePosition> datasourcePositions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public DatasourceType getType() {
        return type;
    }

    public void setType(DatasourceType type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDatasourceLink() {
        return datasourceLink;
    }

    public void setDatasourceLink(String datasourceLink) {
        this.datasourceLink = datasourceLink;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Set<DatasourcePosition> getDatasourcePositions() {
        return datasourcePositions;
    }

    public void setDatasourcePositions(Set<DatasourcePosition> datasourcePositions) {
        this.datasourcePositions = datasourcePositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Datasource datasource = (Datasource) o;

        if ( ! Objects.equals(id, datasource.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Datasource{" +
            "id=" + id +
            ", condition='" + condition + "'" +
            ", type='" + type + "'" +
            ", value='" + value + "'" +
            ", code='" + code + "'" +
            ", datasourceLink='" + datasourceLink + "'" +
            ", sql='" + sql + "'" +
            ", script='" + script + "'" +
            ", description='" + description + "'" +
            ", url='" + url + "'" +
            ", request='" + request + "'" +
            ", response='" + response + "'" +
            '}';
    }
}
