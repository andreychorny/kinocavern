import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Genre } from '../common/genre';

@Injectable({
  providedIn: 'root'
})
export class GenreService {

  private baseUrl = 'http://localhost:8080/api/genres';

  constructor(private httpClient: HttpClient) { }

  getGenreList(): Observable<Genre[]> {
    return this.httpClient.get<Genre[]>(this.baseUrl);
  }

}
