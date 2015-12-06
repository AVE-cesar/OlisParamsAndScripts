package com.caceis.olis.paramsandscripts.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import java.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.caceis.olis.paramsandscripts.domain.enumeration.Level;

import com.caceis.olis.paramsandscripts.domain.enumeration.BoolValue;

/**
 * table des organisations (ORG)
 */
@Entity
@Table(name = "ORG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agacorganization")
public class AGACOrganization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ORG_IDE")
    private Long id;

    @Size(max = 100)
    @Column(name = "ORG_CDE", length = 100)
    private String code;

    @Size(max = 100)
    @Column(name = "LIB", length = 100)
    private String name;

    @Column(name = "ORG_TYP_IDE")
    private Long type;

    @Column(name = "IDE_STA")
    private Long status;

    @Column(name = "ROO_ORG_IDE")
    private Long rootOrganization;

    @Column(name = "MOO_ORG_IDE")
    private Long fatherOrganization;

    @Size(max = 10)
    @Column(name = "DES_IDE", length = 10)
    private String theme;

    @Size(max = 30)
    @Column(name = "ORG_MAL", length = 30)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "LVL")
    private Level level;

    @Enumerated(EnumType.STRING)
    @Column(name = "INL")
    private BoolValue internal;

    @NotNull
    @Column(name = "CRE_DTE", nullable = false)
    private LocalDate creationDate;

    @NotNull
    @Column(name = "CRE_USR", nullable = false)
    private Long creatorUserId;

    @Column(name = "MOD_DTE", nullable = false)
    private LocalDate modificationDate;

    @Column(name = "UPD_USR")
    private Long updatorUserId;

    @OneToMany(mappedBy = "agacOrganization")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AGACUserLink> agacUserLinks = new HashSet<>();

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

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getRootOrganization() {
        return rootOrganization;
    }

    public void setRootOrganization(Long rootOrganization) {
        this.rootOrganization = rootOrganization;
    }

    public Long getFatherOrganization() {
        return fatherOrganization;
    }

    public void setFatherOrganization(Long fatherOrganization) {
        this.fatherOrganization = fatherOrganization;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public BoolValue getInternal() {
        return internal;
    }

    public void setInternal(BoolValue internal) {
        this.internal = internal;
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

    public Set<AGACUserLink> getAgacUserLinks() {
        return agacUserLinks;
    }

    public void setAgacUserLinks(Set<AGACUserLink> aGACUserLinks) {
        this.agacUserLinks = aGACUserLinks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AGACOrganization aGACOrganization = (AGACOrganization) o;

        if ( ! Objects.equals(id, aGACOrganization.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AGACOrganization{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", status='" + status + "'" +
            ", rootOrganization='" + rootOrganization + "'" +
            ", fatherOrganization='" + fatherOrganization + "'" +
            ", theme='" + theme + "'" +
            ", email='" + email + "'" +
            ", level='" + level + "'" +
            ", internal='" + internal + "'" +
            ", creationDate='" + creationDate + "'" +
            ", creatorUserId='" + creatorUserId + "'" +
            ", modificationDate='" + modificationDate + "'" +
            ", updatorUserId='" + updatorUserId + "'" +
            '}';
    }
}
