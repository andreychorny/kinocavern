import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
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

const routes = [
  { path: 'movielist', component: MovieListComponent },
  { path: 'signin', component: LoginComponent },
  { path: 'signup', component: RegisterComponent },
  { path: '', redirectTo: 'movielist', pathMatch: 'full' }];


@NgModule({
  declarations: [
    AppComponent,
    MovieListComponent,
    LoginComponent,
    RegisterComponent,
    HeaderComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot(routes)
  ],
  providers: [MovieService, AuthenticationService, TokenStorageService, 
    authInterceptorProviders],
  bootstrap: [AppComponent]
})
export class AppModule { }
