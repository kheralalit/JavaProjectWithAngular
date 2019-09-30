package com.pub.rest.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Author implements Serializable {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -819040727543142700L;

	private int id;
	@NotNull(message = "Name is required")
	@Size(min = 1, max = 100, message = "Data is too long to store")
	private String name;

	public Author() {

	}

	/**
	 * @param id
	 * @param name
	 */
	public Author(int id) {
		super();
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
