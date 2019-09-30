package com.pub.dao.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pub.dao.exception.FindException;
import com.pub.dao.exception.InsertException;
import com.pub.dao.exception.UpdateException;
import com.pub.dao.model.AuthorModel;
import com.pub.dao.model.PublicationModel;
import com.pub.dao.repository.AuthorRepository;
import com.pub.dao.repository.PublicationRepository;
import com.pub.rest.model.Author;
import com.pub.rest.model.PaginationResult;
import com.pub.rest.model.Publication;
import com.pub.rest.model.PublicationPageRequest;
import com.pub.rest.util.Constant;

@Service
@Transactional
public class PublicationService {
	@Autowired
	private PublicationRepository publicationRepository;
	@Autowired
	private AuthorRepository authorRepository;

	/**
	 * Add the publication
	 * 
	 * @param publication - Publication instance
	 */
	public void addPublication(Publication publication) {
		try {
			PublicationModel publicationModel = new PublicationModel();
			BeanUtils.copyProperties(publication, publicationModel, "id");
			List<AuthorModel> authorModels = new ArrayList<>();
			for (Author author : publication.getAuthors()) {
				Optional<AuthorModel> optional = authorRepository
						.findById(author.getId());
				if (optional.isPresent()) {
					authorModels.add(optional.get());
				} else {
					throw new FindException("Author does not exist");
				}
			}
			publicationModel.setAuthors(authorModels);
			publicationRepository.save(publicationModel);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new InsertException("Failed to insert the publication");
		}
	}

	/**
	 * Update the publication
	 * 
	 * @param publication - Publication instance
	 */
	public void updatePublication(Publication publication) {
		try {
			Optional<PublicationModel> optional1 = publicationRepository
					.findById(publication.getId());
			if (!optional1.isPresent()) {
				throw new FindException("Publication not exist");
			}
			PublicationModel publicationModel = optional1.get();
			BeanUtils.copyProperties(publication, publicationModel, "id");
			List<AuthorModel> authorModels = new ArrayList<AuthorModel>();
			for (Author author : publication.getAuthors()) {
				Optional<AuthorModel> optional = authorRepository
						.findById(author.getId());
				if (optional.isPresent()) {
					authorModels.add(optional.get());
				} else {
					throw new FindException("Author does not exist");
				}
			}
			publicationModel.setAuthors(authorModels);
			publicationRepository.save(publicationModel);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new UpdateException("Failed to insert the publication");
		}
	}

	public PaginationResult getPublications(
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

		List<Publication> publications = new ArrayList<Publication>();

		List<PublicationModel> publicationModels = publicationRepository
				.findAll(searchText.toLowerCase(), pageable);
		if (null != publicationModels) {
			for (PublicationModel publicationModel : publicationModels) {
				Publication publication = new Publication();
				BeanUtils.copyProperties(publicationModel, publication);
				List<Author> authors = new ArrayList<Author>();
				for (AuthorModel authorModel : publicationModel.getAuthors()) {
					Author author = new Author();
					BeanUtils.copyProperties(authorModel, author);
					authors.add(author);
				}
				publication.setAuthors(authors);
				publications.add(publication);
			}

		}
		final long count = publicationRepository
				.countPublications(searchText.toLowerCase());
		final PaginationResult paginationResult = new PaginationResult();
		paginationResult.setTotal(count);
		paginationResult.setData(publications);
		return paginationResult;
	}

	public Publication getPublicationById(int id) {
		Optional<PublicationModel> optional = publicationRepository
				.findById(id);
		if (!optional.isPresent()) {
			throw new FindException("Publication not exist");
		}
		Publication publication = new Publication();
		PublicationModel publicationModel = optional.get();
		BeanUtils.copyProperties(publicationModel, publication);
		List<Author> authors = new ArrayList<Author>();
		for (AuthorModel authorModel : publicationModel.getAuthors()) {
			Author author = new Author();
			BeanUtils.copyProperties(authorModel, author);
			authors.add(author);
		}
		publication.setAuthors(authors);
		return publication;
	}

	public void deletePublication(int id) {
		Optional<PublicationModel> optional = publicationRepository
				.findById(id);
		if (!optional.isPresent()) {
			throw new FindException("Publication not exist");
		}
		try {
			publicationRepository.deleteById(id);
		} catch (Exception e) {
			throw new FindException("Failed to delete the publication");
		}
	}

	public List<Publication> getPublications(int year, String author) {
		List<Publication> publications = new ArrayList<Publication>();
		List<PublicationModel> publicationModels = publicationRepository
				.findByYear(year);
		if (null != publicationModels) {
			List<AuthorModel> authorModels = new ArrayList<AuthorModel>();
			for (PublicationModel publicationModel : publicationModels) {
				List<AuthorModel> filter = publicationModel.getAuthors()
						.stream()
						.filter(x -> author.equalsIgnoreCase(x.getName()))
						.collect(Collectors.toList());
				authorModels.addAll(filter);
			}
			publicationModels = publicationRepository
					.findByAuthors(authorModels);
			for (PublicationModel publicationModel : publicationModels) {
				Publication publication = new Publication();
				BeanUtils.copyProperties(publicationModel, publication);
				List<Author> authors = new ArrayList<Author>();
				for (AuthorModel authorModel : publicationModel.getAuthors()) {
					Author author1 = new Author();
					BeanUtils.copyProperties(authorModel, author1);
					authors.add(author1);
				}
				publication.setAuthors(authors);
				publications.add(publication);
			}
		}
		return publications;
	}

	public List<Publication> getPublications(String attribute, String author) {
		List<Publication> publications = new ArrayList<Publication>();
		List<PublicationModel> publicationModels = publicationRepository
				.findByAttribute(attribute.toLowerCase());
		if (null != publicationModels) {
			List<AuthorModel> authorModels = new ArrayList<AuthorModel>();
			for (PublicationModel publicationModel : publicationModels) {
				List<AuthorModel> filter = publicationModel.getAuthors()
						.stream()
						.filter(x -> author.equalsIgnoreCase(x.getName()))
						.collect(Collectors.toList());
				authorModels.addAll(filter);
			}
			publicationModels = publicationRepository
					.findByAuthors(authorModels);
			for (PublicationModel publicationModel : publicationModels) {
				Publication publication = new Publication();
				BeanUtils.copyProperties(publicationModel, publication);
				List<Author> authors = new ArrayList<Author>();
				for (AuthorModel authorModel : publicationModel.getAuthors()) {
					Author author1 = new Author();
					BeanUtils.copyProperties(authorModel, author1);
					authors.add(author1);
				}
				publication.setAuthors(authors);
				publications.add(publication);
			}
		}
		return publications;
	}
}
