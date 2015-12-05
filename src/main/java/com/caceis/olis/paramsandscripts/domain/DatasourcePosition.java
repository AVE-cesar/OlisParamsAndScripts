package com.caceis.olis.paramsandscripts.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * PAR_DTA_LST_LNK
 */
@Entity
@Table(name = "PAR_DTA_LST_LNK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "datasourceposition")
public class DatasourcePosition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PAR_DTA_LST_LNK_IDE")
    private Long id;

    @Column(name = "PAR_DTA_LST_ORD")
    private Long order;

    @ManyToOne
    @JoinColumn(name="RPT_PAR_IDE")
    private Prompt prompt;

    @ManyToOne
    @JoinColumn(name="PAR_DTA_LST_IDE")
    private Datasource datasource;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrder() {
        return order;
    }

    public void setOrder(Long order) {
        this.order = order;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    public Datasource getDatasource() {
        return datasource;
    }

    public void setDatasource(Datasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DatasourcePosition datasourcePosition = (DatasourcePosition) o;

        if ( ! Objects.equals(id, datasourcePosition.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DatasourcePosition{" +
            "id=" + id +
            ", order='" + order + "'" +
            '}';
    }
}
