import { Injectable } from '@angular/core';
import { HttpHeaders, HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { Publication } from '../model/publication';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PublicationService {
  private static readonly ADD_PUBLICATION_URL = '/publication/add';
  private static readonly UPDATE_PUBLICATION_URL = '/publication/update';
  private static readonly DELETE_PUBLICATION_URL = '/publication/delete/';
  private static readonly GET_ALL_PUBLICATION_URL = '/publication/get';
  private static readonly SEACRH_BY_YEAR_AND_AUTHOR_URL = '/publication/search/';
  private static readonly SEACRH_BY_ATTRIBUTE_AND_AUTHOR_URL = '/publication/search';
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient, private router: Router) { }

  public getAll(page: number, direction: string, orderBy: string, size: number, searchText: string): Observable<Publication> {
    return this.http.get<Publication>(PublicationService.GET_ALL_PUBLICATION_URL + "?page=" + page.toString() + "&direction=" + direction + "&orderBy=" + orderBy + "&size=" + size.toString() + "&searchText=" + searchText,
      { headers: this.headers })
  };
  public add(publication: Publication): Observable<Publication> {
    let authorJson = JSON.stringify(publication);
    return this.http.post<Publication>(PublicationService.ADD_PUBLICATION_URL, authorJson,
      { headers: this.headers, responseType: 'json' })
  };
  
  public update(Publication: Publication): Observable<Publication> {
    let authorJson = JSON.stringify(Publication);
    return this.http.put<Publication>(PublicationService.UPDATE_PUBLICATION_URL, authorJson,
      { headers: this.headers, responseType: 'json' })
  };
  public delete(id: String): Observable<Publication> {
    return this.http.delete<Publication>(PublicationService.DELETE_PUBLICATION_URL + id,
      { headers: this.headers })
  };
  public searchByYearAndAuthor(year: number, authorName: string): Observable<Publication> {
    return this.http.get<Publication>(PublicationService.SEACRH_BY_YEAR_AND_AUTHOR_URL+year.toString()+ "?author=" + authorName,
      { headers: this.headers })
  };
  public searchByYearAndAttribute(attribute: string, authorName: string): Observable<Publication> {
    return this.http.get<Publication>(PublicationService.SEACRH_BY_ATTRIBUTE_AND_AUTHOR_URL+"?attribute="+attribute+"author=" + authorName,
      { headers: this.headers })
  };
}
