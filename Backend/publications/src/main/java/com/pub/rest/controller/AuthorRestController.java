/**
 * 
 */
package com.pub.rest.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pub.dao.service.AuthorService;
import com.pub.rest.model.Author;
import com.pub.rest.model.PublicationPageRequest;
import com.pub.rest.model.Response;
import com.pub.rest.util.Constant;

@RestController
@RequestMapping("/author")
public class AuthorRestController {
	@Autowired
	private AuthorService authorService;

	@PostMapping("/add")
	public ResponseEntity<Response> addAuthor(
			@RequestBody @NotNull @Valid Author author) {
		authorService.addAuthor(author);
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				"name", "Desc", 0, 10);
		Response response = getResponse(publicationPageRequest, "");
		response.setMessage("Author is added successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> updateAuthor(
			@RequestBody @NotNull @Valid Author author) {
		authorService.updateAuthor(author);
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				"name", "Desc", 0, 10);
		Response response = getResponse(publicationPageRequest, "");
		response.setMessage("Author is updated successfully");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteAuthor(@PathVariable("id") int id) {
		authorService.deleteAuthor(id);
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				"name", "Desc", 0, 10);
		Response response = getResponse(publicationPageRequest, "");
		response.setMessage("Author is deleted successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getAuthorById(@PathVariable("id") int id) {
		Author author = authorService.getAuthorById(id);
		Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(author);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get")
	public ResponseEntity<Response> getAll(
			@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction,
			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("searchText") String searchText) {
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				orderBy, direction, page, size);

		return ResponseEntity
				.ok(getResponse(publicationPageRequest, searchText));
	}

	@GetMapping("/getAll")
	public ResponseEntity<Response> getAll() {
		return ResponseEntity.ok(getResponse());
	}

	private Response getResponse() {
		final Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(authorService.getAllAuthors());
		return response;
	}

	/**
	 * Get the response
	 * 
	 * @param searchText
	 * @param publicationPageRequest
	 *
	 * @return {@link Response} insatance
	 */
	private Response getResponse(PublicationPageRequest publicationPageRequest,
			String searchText) {
		final Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(authorService.getAllAuthors(publicationPageRequest,
				searchText));
		return response;
	}
}
