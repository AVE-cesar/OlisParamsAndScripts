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
 * table de relations entre les profiles et les utilisateurs (USR_AUT_PRO)
 */
@Entity
@Table(name = "USR_AUT_PRO")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "authorizationsetlink")
public class AuthorizationSetLink implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USR_AUT_PRO_IDE")
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
    @JoinColumn(name="AUT_PRO_IDE")
    private AuthorizationSet authorizationSet;

    @ManyToOne
    @JoinColumn(name="USR_IDE")
    private AGACUser agacUser;

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

    public AGACUser getAgacUser() {
        return agacUser;
    }

    public void setAgacUser(AGACUser aGACUser) {
        this.agacUser = aGACUser;
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
