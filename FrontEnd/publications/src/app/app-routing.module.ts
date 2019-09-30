import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { AuthorComponent } from './author/author.component';
import { PublicationComponent } from './publication/publication.component';


const routes: Routes = [
  { path: '', component: AuthorComponent }
  , { path: 'author', component: AuthorComponent },
  { path: 'publication', component: PublicationComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
