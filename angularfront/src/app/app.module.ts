import { BrowserModule } from '@angular/platform-browser';
import { NgModule, Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { MovieListComponent } from './components/movie-list/movie-list.component';
import {HttpClientModule} from '@angular/common/http';
import { MovieService } from './services/movie.service';
import { RouterModule } from '@angular/router';
import { LoginComponent } from './components/auth/login/login.component';
import { RegisterComponent } from './components/auth/register/register.component';
import { AuthenticationService } from './services/authentication.service';
import { TokenStorageService } from './services/token-storage.service';
import { authInterceptorProviders } from './interceptor/authentication.interceptor';
import { HeaderComponent } from './components/header/header.component';
import { MovieDetailsComponent } from './components/movie-details/movie-details.component';
import { AddMovieComponent } from './components/admin/add-movie/add-movie.component';
import { UserRoleDirective } from './directives/user-role.directive';
import { EditMovieComponent } from './components/admin/edit-movie/edit-movie.component';
import { AttachGenresComponent } from './components/admin/attach-genres/attach-genres.component';
import { AttachCountriesComponent } from './components/admin/attach-countries/attach-countries.component';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import { SearchComponent } from './components/search/search.component';
import { UserRateComponent } from './components/user-rate/user-rate.component';


const routes = [
  { path: 'movielist', component: MovieListComponent },
  { path: 'signin', component: LoginComponent },
  { path: 'signup', component: RegisterComponent },
  { path: 'admin/addMovie', component: AddMovieComponent},
  { path: 'admin/editMovie/:id', component: EditMovieComponent},
  { path: 'movie/:id', component: MovieDetailsComponent},
  { path: '', redirectTo: 'movielist', pathMatch: 'full' }];


@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent,
    MovieDetailsComponent,
    AddMovieComponent,
    UserRoleDirective,
    EditMovieComponent,
    AttachGenresComponent,
    AttachCountriesComponent,
    SearchComponent,
    UserRateComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    NgbModule,
    RouterModule.forRoot(routes)
  ],
  providers: [MovieService, AuthenticationService, TokenStorageService, 
    authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
