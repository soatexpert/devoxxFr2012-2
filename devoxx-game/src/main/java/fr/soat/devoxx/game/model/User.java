package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    @Column(name = "NAME", unique = true)
    String userName;

    @Column(name = "FORNAME")
    String userForname;

    @Column(name = "EMAIL")
    String userEmail;
    
    @Column(columnDefinition = "BOOLEAN", nullable = false)
    private boolean isAdmin;

    @OneToMany
    List<BundleUserQuestions> bundleUserQuestions;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userName=" + userName + ", userForname=" + userForname + ", userEmail=" + userEmail + ", isAdmin=" + isAdmin
                + ", bundleUserQuestions=" + bundleUserQuestions + "]";
    }
}
