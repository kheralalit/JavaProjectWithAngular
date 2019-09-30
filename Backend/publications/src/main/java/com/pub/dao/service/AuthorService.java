package com.pub.dao.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pub.dao.exception.FindException;
import com.pub.dao.exception.InsertException;
import com.pub.dao.model.AuthorModel;
import com.pub.dao.repository.AuthorRepository;
import com.pub.rest.model.Author;
import com.pub.rest.model.PaginationResult;
import com.pub.rest.model.PublicationPageRequest;
import com.pub.rest.util.Constant;

@Service
@Transactional
public class AuthorService {
	@Autowired
	private AuthorRepository authorRepository;

	/**
	 * Add the author
	 * 
	 * @param author - Author instance
	 */
	public void addAuthor(Author author) {
		try {
			AuthorModel authorModel = new AuthorModel();
			BeanUtils.copyProperties(author, authorModel, "id");
			authorRepository.save(authorModel);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InsertException("Failed to insert the author");
		}
	}

	/**
	 * Update the author
	 * 
	 * @param author - Author instance
	 */
	public void updateAuthor(Author author) {
		AuthorModel authorModel = null;
		try {
			authorModel = authorRepository.getOne(author.getId());
		} catch (Exception e) {
			throw new FindException("Author does not exist");
		}
		if (null == authorModel)
			throw new FindException("Author does not exist");
		try {
			BeanUtils.copyProperties(author, authorModel);
			authorRepository.save(authorModel);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InsertException("Failed to updaye the author");
		}
	}

	/**
	 * Delete the author
	 * 
	 * @param authorId - Author Id
	 */
	public void deleteAuthor(int authorId) {
		AuthorModel authorModel = null;
		try {
			authorModel = authorRepository.getOne(authorId);
		} catch (Exception e) {
			throw new FindException("Author does not exist");
		}
		if (null == authorModel)
			throw new FindException("Author does not exist");
		try {
			authorRepository.deleteById(authorId);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InsertException("Failed to updaye the author");
		}
	}

	/**
	 * Get the author by Id
	 * 
	 * @param authorId - Author Id
	 */
	public Author getAuthorById(int authorId) {
		AuthorModel authorModel = null;
		try {
			authorModel = authorRepository.getOne(authorId);
		} catch (Exception e) {
			throw new FindException("Author does not exist");
		}
		if (null == authorModel)
			throw new FindException("Author does not exist");
		Author author = new Author();
		BeanUtils.copyProperties(authorModel, author);
		return author;
	}

	/**
	 * Get the authors
	 * 
	 * @param searchText
	 * @param publicationPageRequest
	 * 
	 * @return List of authors
	 */
	public PaginationResult getAllAuthors(
			PublicationPageRequest publicationPageRequest, String searchText) {
		if (null == searchText || searchText.trim().isEmpty())
			searchText = Constant.BLANK;
		String orderBy = publicationPageRequest.getOrderBy();
		Sort sort = Sort.by(new Sort.Order(Direction.DESC, orderBy));
		String direction = publicationPageRequest.getDirection();

		if (Constant.ASC.equals(direction)) {
			sort = Sort.by(new Sort.Order(Direction.ASC, orderBy));
		}
		Pageable pageable = PageRequest.of(publicationPageRequest.getPage(),
				publicationPageRequest.getSize(), sort);

		List<Author> authors = new ArrayList<>();
		Page<AuthorModel> authorModels = authorRepository
				.findAll(searchText.toLowerCase(), pageable);
		if (null != authorModels) {
			final Iterator<AuthorModel> iterator = authorModels.iterator();
			while (iterator.hasNext()) {
				AuthorModel authorModel = iterator.next();
				Author author = new Author();
				BeanUtils.copyProperties(authorModel, author);
				authors.add(author);
			}
		}
		final long count = authorRepository
				.countAuthors(searchText.toLowerCase());
		final PaginationResult paginationResult = new PaginationResult();
		paginationResult.setTotal(count);
		paginationResult.setData(authors);
		return paginationResult;
	}

	public Object getAllAuthors() {
		List<Author> authors = new ArrayList<>();
		List<AuthorModel> authorModels = authorRepository.findAll();
		if (null != authorModels) {
			final Iterator<AuthorModel> iterator = authorModels.iterator();
			while (iterator.hasNext()) {
				AuthorModel authorModel = iterator.next();
				Author author = new Author();
				BeanUtils.copyProperties(authorModel, author);
				authors.add(author);
			}
		}
		return authors;
	}

}
