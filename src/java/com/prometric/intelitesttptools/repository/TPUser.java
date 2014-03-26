package com.prometric.intelitesttptools.Repository;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
/**
 * @author Patrick.MacCnaimhin
 */
@Entity
@Table(name = "TPUser")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TPUser.findAll", query = "SELECT t FROM TPUser t"),
    @NamedQuery(name = "TPUser.findById", query = "SELECT t FROM TPUser t WHERE t.id = :id"),
    @NamedQuery(name = "TPUser.findByFirstname", query = "SELECT t FROM TPUser t WHERE t.firstname = :firstname"),
    @NamedQuery(name = "TPUser.findByLastname", query = "SELECT t FROM TPUser t WHERE t.lastname = :lastname"),
    @NamedQuery(name = "TPUser.findByPassword", query = "SELECT t FROM TPUser t WHERE t.password = :password"),
    @NamedQuery(name = "TPUser.findBySince", query = "SELECT t FROM TPUser t WHERE t.since = :since"),
    @NamedQuery(name = "TPUser.findByUsername", query = "SELECT t FROM TPUser t WHERE t.username = :username")})
public class TPUser implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(generator="MySeq")
    @SequenceGenerator(name="MySeq",sequenceName="seq_Name", allocationSize=1)
    @Column(name = "ID")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "FIRSTNAME")
    private String firstname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "LASTNAME")
    private String lastname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SINCE", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date since;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "USERNAME")
    private String username;

    public TPUser() {
    }

    public TPUser(Integer id) {
        this.id = id;
    }

    public TPUser(Integer id, String firstname, String lastname, String password, Date since, String username) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.since = since;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TPUser)) {
            return false;
        }
        TPUser other = (TPUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.prometric.intelitesttptools.membership.TPUser[ id=" + id + " ]";
    }
    
}
