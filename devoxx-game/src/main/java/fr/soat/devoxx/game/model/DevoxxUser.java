package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.google.common.base.Strings;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "DEVOXX_USER")
public class DevoxxUser implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long userId;

    @Column(name = "NAME", unique = true, nullable = false)
    String username;

    @Column(name = "FORNAME")
    String userForname;

    @Column(name = "EMAIL")
    String userEmail;

    @Column(name = "REGEMENT_ACCEPTED")
    boolean reglementAccepted;

    @Column(name = "COMMERCIAL_ACCEPTED")
    boolean commercialEmailAccepted;

    @Column(name = "NEWS_ACCEPTED")
    boolean nextEventsAccepted;

    @Column(name = "QRCODE_ACCPETED")
    boolean isAcceptedQrCode;
    
    @Column(name = "IS_ENABLED")
    boolean enabled = false;

    /*@OneToMany
    List<BundleUserQuestions> bundleUserQuestions;*/    
    @OneToOne
    BundleUserQuestions bundleUserQuestions;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_USER_ROLES", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    Set<UserRole> userRoles = new HashSet<UserRole>();


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserForname() {
        return userForname;
    }

    public void setUserForname(String userForname) {
        this.userForname = userForname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
        for (UserRole role : this.getUserRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }        
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return username;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String userName) {
        this.username = userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }   
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    /*public List<BundleUserQuestions> getBundleUserQuestions() {
        return bundleUserQuestions;
    }

    public void setBundleUserQuestions(List<BundleUserQuestions> bundleUserQuestions) {
        this.bundleUserQuestions = bundleUserQuestions;
    }*/
    
    public BundleUserQuestions getBundleUserQuestions() {
        return bundleUserQuestions;
    }

    public void setBundleUserQuestions(BundleUserQuestions bundleUserQuestions) {
        this.bundleUserQuestions = bundleUserQuestions;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public void addUserRole(UserRole userRole) {
        this.userRoles.add(userRole);
    }

    public void setReglementAccepted(boolean reglementAccepted) {
        this.reglementAccepted = reglementAccepted;
    }

    public boolean isReglementAccepted() {
        return reglementAccepted;
    }

    @Override
    public String toString() {
        return "DevoxxUser [userId=" + userId + ", username=" + username + ", userForname=" + userForname + ", userEmail=" + userEmail + ", reglementAccepted="
                + reglementAccepted + ", commercialEmailAccepted=" + commercialEmailAccepted + ", nextEventsAccepted=" + nextEventsAccepted
                + ", isAcceptedQrCode=" + isAcceptedQrCode + ", enabled=" + enabled + ", userRoles=" + userRoles + "]";
    }

    @Transient
    public String getMailHash() {
        String n_email = Strings.isNullOrEmpty(userEmail) ? "" : userEmail.trim().toLowerCase();
        return DigestUtils.md5Hex(n_email);
    }
}
