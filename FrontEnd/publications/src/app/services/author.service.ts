import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Author } from '../model/author'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from "@angular/router"

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private static readonly ADD_AUTHOR_URL = '/author/add';
  private static readonly UPDATE_AUTHOR_URL = '/author/update';
  private static readonly DELETE_AUTHOR_URL = '/author/delete/';
  private static readonly GET_ALL_AUTHOR_URL = '/author/get';
  private static readonly GET_AUTHOR_URL = '/author/getAll';
  private headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  constructor(private http: HttpClient, private router: Router) { }

  public getAll(page: number, direction: string, orderBy: string, size: number, searchText: string): Observable<Author> {
    return this.http.get<Author>(AuthorService.GET_ALL_AUTHOR_URL + "?page=" + page.toString() + "&direction=" + direction + "&orderBy=" + orderBy + "&size=" + size.toString() + "&searchText=" + searchText,
      { headers: this.headers })
  };
  public get(): Observable<Author> {
    return this.http.get<Author>(AuthorService.GET_AUTHOR_URL,
      { headers: this.headers })
  };
  public add(Author: Author): Observable<Author> {
    let authorJson = JSON.stringify(Author);
    return this.http.post<Author>(AuthorService.ADD_AUTHOR_URL, authorJson,
      { headers: this.headers, responseType: 'json' })
  };
  public update(Author: Author): Observable<Author> {
    let authorJson = JSON.stringify(Author);
    return this.http.put<Author>(AuthorService.UPDATE_AUTHOR_URL, authorJson,
      { headers: this.headers, responseType: 'json' })
  };
  public delete(id: String): Observable<Author> {
    return this.http.delete<Author>(AuthorService.DELETE_AUTHOR_URL + id,
      { headers: this.headers })
  };
}
