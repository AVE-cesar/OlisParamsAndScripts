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
 * RPT_PAR_LNK
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "RPT_PAR_LNK")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "promptposition")
public class PromptPosition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RPT_PAR_LNK_IDE")
    private Long id;

    @Column(name = "RPT_PAR_LNK_ORD")
    private Long order;

    @ManyToOne
    @JoinColumn(name="RPT_IDE")
    private Report report;

    @ManyToOne
    @JoinColumn(name="RPT_PAR_IDE")
    private Prompt prompt;

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

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    public Prompt getPrompt() {
        return prompt;
    }

    public void setPrompt(Prompt prompt) {
        this.prompt = prompt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PromptPosition promptPosition = (PromptPosition) o;

        if ( ! Objects.equals(id, promptPosition.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PromptPosition{" +
            "id=" + id +
            ", order='" + order + "'" +
            '}';
    }
}
