import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Movie } from '../common/movie';
import { Genre } from '../common/genre';
import { Country } from '../common/country';
import { createOfflineCompileUrlResolver } from '@angular/compiler';
import { Category } from '../common/category';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class MovieService {

  private baseUrl = 'http://localhost:8080/api/movies';

  constructor(private httpClient: HttpClient) { }

  getMovieList(pageNumber: number, selectedOrder: string, categoryId: number,
              genreId: number): Observable<Movie[]> {
    let params = new HttpParams();
    params = params.set("page", String(pageNumber));
    params = params.set("orderBy", selectedOrder);
    if (categoryId != null) { params = params.set("categoryId", String(categoryId)); }
    if (genreId != null) { params = params.set("genreId", String(genreId)); }
    return this.httpClient.get<Movie[]>(this.baseUrl, {params});
  }

  postMovie(movie: Movie, file: File, genresIds: number[],
            countriesIds: number[], categoryId: number): Observable<any> {
    const formData: FormData = new FormData();
    const title = movie.title;
    const year = movie.year;
    const fullInfo = {title, year, genresIds, countriesIds, categoryId};

    formData.append('uploadFile', file);
    formData.append('info', new Blob([JSON.stringify(fullInfo)],
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

  updateMovie(movie: Movie, category: Category, genres: Genre[],
              countries: Country[]): Observable<any>{
    const id = movie.id;
    const title = movie.title;
    const year = movie.year;
    const imageUrl = movie.imageUrl;
    const fullMovie = {id, title, year, imageUrl, category, genres, countries};
    return this.httpClient.put(this.baseUrl, fullMovie, httpOptions);
  }

  updateTitleImg(file: File, imgUrl: string): Observable<any> {
    const formData: FormData = new FormData();
    const updateUrl = `${this.baseUrl}/updateTitleImg`;

    formData.append('file', file);
    formData.append('imgUrl', imgUrl);
    return this.httpClient.put(updateUrl, formData);

  }
}
