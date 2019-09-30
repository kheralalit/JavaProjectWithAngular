import { Component, OnInit } from '@angular/core';
import { Author } from '../model/author';
import { AuthorService } from '../services/author.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {
  author: Author;
  error: '';
  success: '';
  authors: [];
  hasError = false;
  hasSuccess = false;
  total: number = 0;
  placeholder: string = "";
  currentPage: number = 0;
  direction: string = "ASC";
  orderBy: string = "name";
  searchText = "";
  size: number = 10;
  pages: number[];
  isEdit = false;
  constructor(private authorService: AuthorService) {
    this.author = new Author;
  }

  ngOnInit() {
    this.loadAuthors();
  }
  loadAuthors(): void {
    this.pages = [];
    this.authorService.getAll(this.currentPage, this.direction, this.orderBy, this.size, this.searchText).subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.authors = data["result"]["data"];
        this.total = data["result"]["total"];
        if (this.total > 0) {
          this.placeholder = + this.total + " number of Author";
          let numberPages = 0;
          if (this.total % this.size != 0) {
            numberPages = Math.round(this.total / this.size);
            numberPages += 1;
          }
          else
            numberPages = this.total % this.size;

          for (let i = 0; i < numberPages; i++) {
            this.pages[i] = i + 1;
          }
        }
      }
    });
  }
  sort() {
    this.direction = "ASC" == this.direction ? "DESC" : "ASC";
    this.currentPage = 0;
    this.loadAuthors();
  }
  public next() {
    if (this.currentPage != this.pages.length) {
      this.currentPage = this.currentPage + 1;
      this.loadAuthors();

    }
  }
  public previous() {
    if (this.currentPage != 0) {
      this.currentPage = this.currentPage - 1;
      this.loadAuthors();

    }
  }
  public loadPage(pageNum: number) {
    this.currentPage = pageNum - 1;
    this.loadAuthors();
  }
  delete(id: number) {
    this.authorService.delete(id.toString()).subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.hasSuccess = true;
        this.loadAuthors();
        this.success = data["message"];
        this.author = new Author;
      } else {
        this.hasError = true;
        this.error = data["message"];
      }
    });
  }
  edit(author) {
    this.author = author;
    this.isEdit = true;
  }
  save() {
    this.hasError = false;
    this.hasSuccess = false;
    if (this.isEdit) {
      this.authorService.update(this.author).subscribe(data => {
        let type = data["type"];
        if ("error" != type) {
          this.hasSuccess = true;
          this.loadAuthors();
          this.success = data["message"];
          this.author = new Author;
          this.isEdit = false;
        } else {
          this.hasError = true;
          this.error = data["message"];
        }
      });
    } else {
      this.authorService.add(this.author).subscribe(data => {
        let type = data["type"];
        if ("error" != type) {
          this.hasSuccess = true;
          this.loadAuthors();
          this.success = data["message"];
          this.author = new Author;
        } else {
          this.hasError = true;
          this.error = data["message"];
        }
      });
    }
  }

}
