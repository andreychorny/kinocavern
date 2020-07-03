import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import {HttpClientModule} from '@angular/common/http';
import { MovieService } from './services/movie.service';

@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [MovieService],
  bootstrap: [AppComponent]
})
export class AppModule { }
