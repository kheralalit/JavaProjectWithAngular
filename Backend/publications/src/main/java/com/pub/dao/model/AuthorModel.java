/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity(name = "Author")
@Table(name = "author")
@XmlRootElement
public class AuthorModel implements Serializable {

	/**
	 * Serial Version
	 */
	private static final long serialVersionUID = -6244350265196729890L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "id")
	private Integer id;
	@Basic(optional = false)
	@Column(name = "name")
	private String name;
	@ManyToMany(mappedBy = "authors", cascade = { CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH })
	private List<PublicationModel> publications;

	public Integer getId() {
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

	@XmlTransient
	public List<PublicationModel> getPublications() {
		return publications;
	}

	public void setPublications(List<PublicationModel> publications) {
		this.publications = publications;
	}
}
