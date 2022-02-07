package com.example.cash.Models;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class User {
    
	private @Id @GeneratedValue Integer id;
    @Column(unique = true)
    private String email;
    private String firstname;
    private String lastname;


    public User() {}

    public User(String email, String firstname, String lastname) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getId() {
        return id;
    }

    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstname;
	}

	public void setFirstName(String firstname) {
		this.firstname = firstname;
	}

	public String getLastName() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}
    
    @Override
    public int hashCode() {
		return Objects.hash(id, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        User user = (User) o;
        
        return Objects.equals(this.id, user.id)
            && Objects.equals(this.email, user.email);
    }

    @Override
    public String toString() {
        // TODO add loans to string
        return "User{" +
                "id=" + id + ", " +
                "email='" + email + '\'' + ", " +
                "firstname='" + firstname + '\'' + ", " +
                "lastname='" + lastname + '\'' +
                '}';

    }
}
