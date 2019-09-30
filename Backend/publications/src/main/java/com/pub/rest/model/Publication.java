package com.pub.rest.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Publication implements Serializable {
	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -4425236608872577732L;

	private int id;

	@NotBlank(message = "Title is required")
	@NotNull(message = "Title is required")
	@Size(min = 1, max = 100, message = "Data is too long")
	private String title;

	@Min(value = 1, message = "Year must be positive non-zero number")
	private Integer year;

	@Lob
	@Size(min = 1, max = 1073741824, message = "Data is too long")
	@NotBlank(message = "Attributes is required")
	@NotNull(message = "Attributes is required")
	private String attributes;

	@Size(min = 4, max = 45, message = "Type is required")
	private String type;

	@NotNull(message = "Authors required")
	private List<Author> authors;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

}
