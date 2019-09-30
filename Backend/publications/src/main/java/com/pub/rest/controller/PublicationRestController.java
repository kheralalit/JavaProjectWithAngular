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

import com.pub.dao.service.PublicationService;
import com.pub.rest.model.Publication;
import com.pub.rest.model.PublicationPageRequest;
import com.pub.rest.model.Response;
import com.pub.rest.util.Constant;

@RestController
@RequestMapping("/publication")
public class PublicationRestController {
	@Autowired
	private PublicationService publicationService;

	@PostMapping("/add")
	public ResponseEntity<Response> addPublication(
			@RequestBody @NotNull @Valid Publication publication) {
		publicationService.addPublication(publication);
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				"title", "Desc", 0, 10);
		Response response = getResponse(publicationPageRequest, "");
		response.setMessage("Publication is added successfully");
		return ResponseEntity.ok(response);
	}

	@PutMapping("/update")
	public ResponseEntity<Response> updatePublication(
			@RequestBody @NotNull @Valid Publication publication) {
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				"title", "Desc", 0, 10);
		publicationService.updatePublication(publication);
		Response response = getResponse(publicationPageRequest, "");
		response.setMessage("Publication is updated successfully");
		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deletePublication(
			@PathVariable(name = "id", required = true) int id) {
		publicationService.deletePublication(id);
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				"title", "Desc", 0, 10);
		Response response = getResponse(publicationPageRequest, "");
		response.setMessage("Publication is deleted successfully");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get/{id}")
	public ResponseEntity<Response> getPublication(
			@PathVariable(name = "id", required = true) int id) {
		Publication publication = publicationService.getPublicationById(id);
		final Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(publication);
		return ResponseEntity.ok(response);
	}

	@GetMapping("/get")
	public ResponseEntity<Response> getPublications(
			@RequestParam("orderBy") String orderBy,
			@RequestParam("direction") String direction,
			@RequestParam("page") int page, @RequestParam("size") int size,
			@RequestParam("searchText") String searchText) {
		final PublicationPageRequest publicationPageRequest = new PublicationPageRequest(
				orderBy, direction, page, size);
		Response response = getResponse(publicationPageRequest, "");
		return ResponseEntity.ok(response);
	}

	@GetMapping("/search/{year}")
	public ResponseEntity<Response> getByYearAndAuthor(
			@PathVariable(name = "year", required = true) int year,
			@RequestParam(name = "author", required = true) String author) {
		return ResponseEntity.ok(getResponse(year, author));
	}

	@GetMapping("/search")
	public ResponseEntity<Response> getByAttributeAndAuthor(
			@RequestParam(name = "attribute", required = true) String attribute,
			@RequestParam(name = "author", required = true) String author) {
		return ResponseEntity.ok(getResponse(attribute, author));
	}

	private Response getResponse(String attribute, String author) {
		final Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(
				publicationService.getPublications(attribute, author));
		return response;
	}

	private Response getResponse(int year, String author) {
		final Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(publicationService.getPublications(year, author));
		return response;
	}

	/**
	 * Get the response
	 *
	 * @return {@link Response} insatance
	 */
	private Response getResponse(PublicationPageRequest publicationPageRequest,
			String searchText) {
		final Response response = new Response();
		response.setType(Constant.SUCCESS);
		response.setResult(publicationService
				.getPublications(publicationPageRequest, searchText));
		return response;
	}
	
}
