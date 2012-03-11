package fr.soat.devoxx.game.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	long userId;
	
	@Column(name = "NAME")
	String userName;

	@Column(name = "FORNAME")
	String userForname;

	@OneToMany
	List<BundleUserQuestions> bundleUserQuestions;

	public long getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
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


}
