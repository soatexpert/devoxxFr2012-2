package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "USER")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long userId;

    @Column(name = "NAME", unique = true, nullable = false)
    String userName;

    @Column(name = "FORNAME")
    String userForname;

    @Column(name = "EMAIL")
    String userEmail;

    @OneToMany
    List<BundleUserQuestions> bundleUserQuestions;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "USER_USER_ROLES", joinColumns = @JoinColumn(name = "ID_USER"), inverseJoinColumns = @JoinColumn(name = "ID_ROLE"))
    List<UserRoles> userRoles = new ArrayList<UserRoles>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return userName;
    }

    public String getUsername() {
        return userName;
    }

    public boolean isAccountNonExpired() {
        return false;
    }

    public boolean isAccountNonLocked() {
        return false;
    }

    public boolean isCredentialsNonExpired() {
        return false;
    }

    public boolean isEnabled() {
        return true;
    }    

    public List<BundleUserQuestions> getBundleUserQuestions() {
        return bundleUserQuestions;
    }

    public void setBundleUserQuestions(List<BundleUserQuestions> bundleUserQuestions) {
        this.bundleUserQuestions = bundleUserQuestions;
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }
    
    public void addUserRole(UserRoles userRole) {
        this.userRoles.add(userRole);
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", userForname=" + userForname + ", userEmail=" + userEmail
                + ", bundleUserQuestions=" + bundleUserQuestions + "]";
    }
}
