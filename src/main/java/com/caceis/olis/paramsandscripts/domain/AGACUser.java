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

import com.caceis.olis.paramsandscripts.domain.enumeration.Gender;

import com.caceis.olis.paramsandscripts.domain.enumeration.Language;

/**
 * table des utilisateurs (AGA_USR)
 */
@Entity
@Table(name = "AGA_USR")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "agacuser")
public class AGACUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USR_IDE")
    private Long id;

    @Column(name = "IDE_STA")
    private Long status;

    @Size(max = 20)
    @Column(name = "USR_NME", length = 20)
    private String login;

    @Size(max = 25)
    @Column(name = "EXT_IDE", length = 25)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "TTL")
    private Gender gender;

    @Size(max = 20)
    @Column(name = "FST_NME", length = 20)
    private String firstName;

    @Size(max = 50)
    @Column(name = "LAS_NME", length = 50)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "LNG")
    private Language language;

    @Size(max = 100)
    @Column(name = "MAL", length = 100)
    private String email;

    @Size(max = 20)
    @Column(name = "TEL", length = 20)
    private String phone;

    @Size(max = 20)
    @Column(name = "CEL", length = 20)
    private String cellularPhone;

    @Size(max = 20)
    @Column(name = "FAX", length = 20)
    private String fax;

    @Column(name = "TYPE_AUTHE")
    private Long authenticationType;

    @Size(max = 20)
    @Column(name = "CD_DESIGN", length = 20)
    private String theme;

    @Column(name = "CRE_DTE")
    private LocalDate creationDate;

    @NotNull
    @Column(name = "CRE_USR", nullable = false)
    private Long creatorUserId;

    @Column(name = "MOD_DTE", nullable = false)
    private LocalDate modificationDate;

    @Column(name = "UPD_USR")
    private Long updatorUserId;

    @OneToMany(mappedBy = "agacUser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AuthorizationSetLink> authorizationSetLinks = new HashSet<>();

    @OneToMany(mappedBy = "agacUser")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AGACUserLink> agacUserLinks = new HashSet<>();

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

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCellularPhone() {
        return cellularPhone;
    }

    public void setCellularPhone(String cellularPhone) {
        this.cellularPhone = cellularPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(Long authenticationType) {
        this.authenticationType = authenticationType;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
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

    public Set<AuthorizationSetLink> getAuthorizationSetLinks() {
        return authorizationSetLinks;
    }

    public void setAuthorizationSetLinks(Set<AuthorizationSetLink> authorizationSetLinks) {
        this.authorizationSetLinks = authorizationSetLinks;
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

        AGACUser aGACUser = (AGACUser) o;

        if ( ! Objects.equals(id, aGACUser.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AGACUser{" +
            "id=" + id +
            ", status='" + status + "'" +
            ", login='" + login + "'" +
            ", externalId='" + externalId + "'" +
            ", gender='" + gender + "'" +
            ", firstName='" + firstName + "'" +
            ", lastName='" + lastName + "'" +
            ", language='" + language + "'" +
            ", email='" + email + "'" +
            ", phone='" + phone + "'" +
            ", cellularPhone='" + cellularPhone + "'" +
            ", fax='" + fax + "'" +
            ", authenticationType='" + authenticationType + "'" +
            ", theme='" + theme + "'" +
            ", creationDate='" + creationDate + "'" +
            ", creatorUserId='" + creatorUserId + "'" +
            ", modificationDate='" + modificationDate + "'" +
            ", updatorUserId='" + updatorUserId + "'" +
            '}';
    }
}
