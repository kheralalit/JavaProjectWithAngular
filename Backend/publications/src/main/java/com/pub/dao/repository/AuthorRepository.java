package com.pub.dao.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pub.dao.model.AuthorModel;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorModel, Integer> {
	
	@Query("From Author as a where lower(a.name) like %?1%")
	Page<AuthorModel> findAll(String searchText, Pageable pageable);

	@Query("SELECT COUNT(a) FROM Author as a WHERE lower(a.name) like %?1%")
	long countAuthors(String searchText);

}
