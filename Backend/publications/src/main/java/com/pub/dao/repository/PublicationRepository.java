package com.pub.dao.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.pub.dao.model.AuthorModel;
import com.pub.dao.model.PublicationModel;

/**
 * Publication repository
 */
public interface PublicationRepository
		extends JpaRepository<PublicationModel, Integer> {
	// Get by years
	List<PublicationModel> findByYear(int year);

	// Get by authors
	List<PublicationModel> findByAuthors(List<AuthorModel> authors);

	// Get by attributes
	@Query("From Publication as p where lower(p.attributes) like %?1%")
	List<PublicationModel> findByAttribute(String attribute);

	@Query("From Publication as p where lower(p.title) like %?1% or lower(p.type) like %?1%")
	List<PublicationModel> findAll(String searchText, Pageable pageable);

	@Query("SELECT COUNT(p) FROM Publication as p WHERE lower(p.title) like %?1% or lower(p.type) like %?1%")
	long countPublications(String searchText);
}
