import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Movie } from '../common/movie';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private baseUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) { }

  getMovieList(): Observable<Movie[]> {
    return this.httpClient.get<Movie[]>(this.baseUrl);
  }

  postMovie(movie: Movie, file: File): Observable<any> {
    const formData: FormData = new FormData();

    formData.append('uploadFile', file);
    formData.append('info', new Blob([JSON.stringify(movie)],
    {
        type: 'application/json'
    }));

    return this.httpClient.post(this.baseUrl, formData);
  }

  getMovie(theMovieId: number): Observable<Movie> {
    // need to build URL based on product id
    const movieUrl = `${this.baseUrl}/${theMovieId}`;
    return this.httpClient.get<Movie>(movieUrl);
  }

  deleteMovie(theMovieId: number): Observable<any>{
    const movieUrl = `${this.baseUrl}/${theMovieId}`;

    return this.httpClient.delete(movieUrl, { responseType: 'text' });
  }

  updateMovie(movie: Movie): Observable<any>{
    return this.httpClient.put(this.baseUrl, movie, httpOptions);
  }

  updateTitleImg(file: File, imgUrl: string): Observable<any> {
    const formData: FormData = new FormData();
    const updateUrl = `${this.baseUrl}/updateTitleImg`;

    formData.append('file', file);
    formData.append('imgUrl', imgUrl);
    return this.httpClient.put(updateUrl, formData);

  }
}
