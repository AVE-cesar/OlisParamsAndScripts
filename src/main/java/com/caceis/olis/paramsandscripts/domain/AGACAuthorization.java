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
 * tables de droits (AUT_SVC)
 */
@Entity
@Table(name = "AUT_SVC")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agacauthorization")
public class AGACAuthorization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUT_SVC_IDE")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "SVC_CDE", length = 100, nullable = false)
    private String code;

    @Size(max = 100)
    @Column(name = "SVC_LIB", length = 100)
    private String name;

    @OneToMany(mappedBy = "agacAuthorization")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AuthorizationLink> authorizationLinks = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AuthorizationLink> getAuthorizationLinks() {
        return authorizationLinks;
    }

    public void setAuthorizationLinks(Set<AuthorizationLink> authorizationLinks) {
        this.authorizationLinks = authorizationLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AGACAuthorization aGACAuthorization = (AGACAuthorization) o;

        if ( ! Objects.equals(id, aGACAuthorization.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AGACAuthorization{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
