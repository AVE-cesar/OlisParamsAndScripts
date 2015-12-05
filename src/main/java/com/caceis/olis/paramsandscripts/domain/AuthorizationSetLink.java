package com.caceis.olis.paramsandscripts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A AuthorizationSetLink.
 */
@Entity
@Table(name = "authorization_set_link")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "authorizationsetlink")
public class AuthorizationSetLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
    private Long id;

    @Column(name = "status")
    private Long status;

    @Column(name = "validity_start_date")
    private LocalDate validityStartDate;

    @Column(name = "validity_end_date")
    private LocalDate validityEndDate;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "creator_user_id")
    private Long creatorUserId;

    @Column(name = "modification_date")
    private LocalDate modificationDate;

    @Column(name = "updator_user_id")
    private Long updatorUserId;

    @ManyToOne
    private AGACUser agacUser;

    @OneToMany(mappedBy = "authorizationSetLink")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AGACAuthorization> agacAuthorizations = new HashSet<>();

    @OneToMany(mappedBy = "authorizationSetLink")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AuthorizationSet> authorizationSets = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public LocalDate getValidityStartDate() {
        return validityStartDate;
    }

    public void setValidityStartDate(LocalDate validityStartDate) {
        this.validityStartDate = validityStartDate;
    }

    public LocalDate getValidityEndDate() {
        return validityEndDate;
    }

    public void setValidityEndDate(LocalDate validityEndDate) {
        this.validityEndDate = validityEndDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Long getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(Long creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getUpdatorUserId() {
        return updatorUserId;
    }

    public void setUpdatorUserId(Long updatorUserId) {
        this.updatorUserId = updatorUserId;
    }

    public AGACUser getAgacUser() {
        return agacUser;
    }

    public void setAgacUser(AGACUser aGACUser) {
        this.agacUser = aGACUser;
    }

    public Set<AGACAuthorization> getAgacAuthorizations() {
        return agacAuthorizations;
    }

    public void setAgacAuthorizations(Set<AGACAuthorization> aGACAuthorizations) {
        this.agacAuthorizations = aGACAuthorizations;
    }

    public Set<AuthorizationSet> getAuthorizationSets() {
        return authorizationSets;
    }

    public void setAuthorizationSets(Set<AuthorizationSet> authorizationSets) {
        this.authorizationSets = authorizationSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorizationSetLink authorizationSetLink = (AuthorizationSetLink) o;

        if ( ! Objects.equals(id, authorizationSetLink.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuthorizationSetLink{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", validityStartDate='" + validityStartDate + "'" +
            ", validityEndDate='" + validityEndDate + "'" +
            ", creationDate='" + creationDate + "'" +
            ", creatorUserId='" + creatorUserId + "'" +
            ", modificationDate='" + modificationDate + "'" +
            ", updatorUserId='" + updatorUserId + "'" +
            '}';
    }
}