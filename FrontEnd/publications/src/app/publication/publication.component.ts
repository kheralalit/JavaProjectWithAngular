import { Component, OnInit } from '@angular/core';
import { Publication } from '../model/publication';
import { PublicationService } from '../services/publication.service';
import { AuthorService } from '../services/author.service';
import { Author } from '../model/author';

export interface Category {
  value: string;
  viewValue: string;
}

export interface Type {
  value: string;
  viewValue: string;
}

export interface Genre {
  value: string;
  viewValue: string;
}


@Component({
  selector: 'app-publication',
  templateUrl: './publication.component.html',
  styleUrls: ['./publication.component.css']
})
export class PublicationComponent implements OnInit {
  publication: Publication;
  isOne=true;
  year=0;
  authorName="";
  attribute1="";
  error: '';
  success: '';
  authors: Author[];
  publications: Publication[];
  hasError = false;
  hasSuccess = false;
  total: number = 0;
  placeholder: string = "";
  currentPage: number = 0;
  direction: string = "ASC";
  orderBy: string = "title";
  searchText = "";
  size: number = 10;
  pages: number[];
  attribute: string;
  isEdit = false;
  isBook = false;
  isMagazine = false;
  searchBy=1;
  categories: Category[] = [
    { value: "Book", viewValue: 'Book' },
    { value: "Comics", viewValue: 'Comics' },
    { value: "Magazine", viewValue: 'Magazine' }
  ];

  types: Type[] = [
    { value: "Printed", viewValue: 'Printed' },
    { value: "Online", viewValue: 'Online' }];

  genres: Genre[] = [
    { value: "Drama", viewValue: 'Drama' },
    { value: "Detective", viewValue: 'Detective' },
    { value: "Novel", viewValue: 'Novel' }];

  constructor(private publicationService: PublicationService, private authorService: AuthorService) {
    this.attribute = "";
    this.publication = new Publication;
  }

  ngOnInit() {
    this.publications = [];
    this.authors = [];
    this.loadPublications();
    this.loadAuthors();
  }
  updateAuthor(authorId: number) {

    let exist = false;
    this.publication.authors.forEach(a => {
      if (a.id == authorId) {
        exist = true
      }
    });
    if (!exist) {
      let auth = new Author;
      auth.id = authorId
      this.publication.authors.push(auth);
    }
  }
  setAttribute(attributes:string){
    this.publication.attributes=attributes;
  }
  onOptionsSelected(category) {
    this.isBook = false;
    this.isMagazine = false;
    if ("Book" == category) {
      this.isBook = true;
    } else if ("Magazine" == category) {
      this.isMagazine = true;
    }
  }
  loadPublications() {
    this.pages = [];
    this.publicationService.getAll(this.currentPage, this.direction, this.orderBy, this.size, this.searchText).subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.publications = data["result"]["data"];
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
  loadAuthors() {
    this.authorService.get().subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.authors = data["result"];
      }
    });
  }

  sort() {
    this.direction = "ASC" == this.direction ? "DESC" : "ASC";
    this.currentPage = 0;
    this.loadPublications();
  }
  public next() {
    if (this.currentPage != this.pages.length) {
      this.currentPage = this.currentPage + 1;
      this.loadPublications();

    }
  }
  public previous() {
    if (this.currentPage != 0) {
      this.currentPage = this.currentPage - 1;
      this.loadPublications();

    }
  }
  public loadPage(pageNum: number) {
    this.currentPage = pageNum - 1;
    this.loadPublications();
  }
  delete(id: number) {
    this.publicationService.delete(id.toString()).subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.hasSuccess = true;
        this.loadPublications();
        this.success = data["message"];
        this.publication = new Publication;
      } else {
        this.hasError = true;
        this.error = data["message"];
      }
    });
  }
  edit(publication: Publication) {
    this.publication = publication;
    this.isEdit = true;
  }
  searchCriteria(searchBy:number){
    this.searchBy=searchBy;
    this.isOne=false;
    if(this.searchBy==1)
        this.isOne=true;
  
  }
  searchByYearAndAuthor(){
    this.total=0;

    this.publicationService.searchByYearAndAuthor(this.year,this.authorName).subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.publications=data["result"];
        this.success = data["message"];
      } else {
        this.hasError = true;
        this.error = data["message"];
      }
    });
  }
  searchByAuthorAndAttribute(){
    this.total=0;

    this.publicationService.searchByYearAndAuthor(this.year,this.authorName).subscribe(data => {
      let type = data["type"];
      if ("error" != type) {
        this.publications=data["result"];
        this.success = data["message"];
      } else {
        this.hasError = true;
        this.error = data["message"];
      }
    });
  }
  save() {
    this.hasError = false;
    this.hasSuccess = false;
    if (this.isEdit) {
      this.publicationService.update(this.publication).subscribe(data => {
        let type = data["type"];
        if ("error" != type) {
          this.hasSuccess = true;
          this.loadPublications();
          this.success = data["message"];
          this.publication = new Publication;
          this.isEdit = false;
        } else {
          this.hasError = true;
          this.error = data["message"];
        }
      });
    } else {
      this.publicationService.add(this.publication).subscribe(data => {
        let type = data["type"];
        if ("error" != type) {
          this.hasSuccess = true;
          this.loadPublications();
          this.success = data["message"];
          this.publication = new Publication;
        } else {
          this.hasError = true;
          this.error = data["message"];
        }
      });
    }
  }

}
