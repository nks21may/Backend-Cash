package com.example.cash.Models;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
public class User {

	private @Id @GeneratedValue Integer id;
	@Column(unique = true)
	private String email;
	private String firstname;
	private String lastname;

	@JsonRawValue
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Loan> loans;

	public User() {
	}

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

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		User user = (User) o;

		return Objects.equals(this.id, user.id) && Objects.equals(this.email, user.email);
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
