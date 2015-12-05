package com.caceis.olis.paramsandscripts.domain;

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
 * A AuthorizationLink.
 */
@Entity
@Table(name = "AUT_PRO_SVC")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "authorizationlink")
public class AuthorizationLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AUT_PRO_SVC_IDE")
    private Long id;

    @Column(name = "IDE_STA")
    private Long status;

    @Column(name = "VAL_STT_DTE")
    private LocalDate validityStartDate;

    @Column(name = "VAL_END_DTE")
    private LocalDate validityEndDate;

    @Column(name = "CRE_DTE")
    private LocalDate creationDate;

    @Column(name = "CRE_USR")
    private Long creatorUserId;

    @Column(name = "UPD_DTE")
    private LocalDate modificationDate;

    @Column(name = "UPD_USR")
    private Long updatorUserId;

    @ManyToOne
    private AuthorizationSet authorizationSet;

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

    public AuthorizationSet getAuthorizationSet() {
        return authorizationSet;
    }

    public void setAuthorizationSet(AuthorizationSet authorizationSet) {
        this.authorizationSet = authorizationSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorizationLink authorizationLink = (AuthorizationLink) o;

        if ( ! Objects.equals(id, authorizationLink.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AuthorizationLink{" +
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
