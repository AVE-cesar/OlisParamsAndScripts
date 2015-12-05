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
 * A AuthorizationSet.
 */
@Entity
@Table(name = "authorization_set")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "authorizationset")
public class AuthorizationSet implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
    private Long id;

    @Size(max = 100)
    @Column(name = "code", length = 100)
    private String code;

    @Size(max = 100)
    @Column(name = "name", length = 100)
    private String name;

    @OneToMany(mappedBy = "authorizationSet")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AuthorizationLink> authorizationLinks = new HashSet<>();

    @ManyToOne
    private AuthorizationSetLink authorizationSetLink;

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

    public AuthorizationSetLink getAuthorizationSetLink() {
        return authorizationSetLink;
    }

    public void setAuthorizationSetLink(AuthorizationSetLink authorizationSetLink) {
        this.authorizationSetLink = authorizationSetLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorizationSet authorizationSet = (AuthorizationSet) o;

        if ( ! Objects.equals(id, authorizationSet.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuthorizationSet{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            '}';
    }
}
