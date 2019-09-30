package com.pub.dao.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "Publication")
@Table(name = "publication")
@XmlRootElement
public class PublicationModel implements Serializable {

	/**
	 * Serial Version UID
	 */
	private static final long serialVersionUID = -758034288777744789L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Column(name = "title")
	private String title;
	@Column(name = "year")
	private Integer year;
	@Column(name = "attributes")
	private String attributes;
	@Column(name = "type")
	private String type;
	@JoinTable(name = "author_publications", joinColumns = {
			@JoinColumn(name = "publication", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "author", referencedColumnName = "id") })
	@ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST})
	private List<AuthorModel> authors;

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

	@XmlTransient
	public List<AuthorModel> getAuthors() {
		return authors;
	}

	public void setAuthors(List<AuthorModel> authors) {
		this.authors = authors;
	}

}
