import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Movie } from '../common/movie';

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private baseUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) { }

  getMovieList(): Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(this.baseUrl);
  }
}
