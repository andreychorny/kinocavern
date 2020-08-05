import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = 'http://localhost:8080/api/users';

  constructor(private httpClient: HttpClient) { }

  postRating(movieId: number, rate: number): Observable<any>{
    const url = this.baseUrl + '/rate';
    const rating = {
      userId: null,
      movieId,
      rate
    };
    return this.httpClient.post(url, rating, httpOptions);
  }

  postToWishlist(movieId: number): Observable<any>{
    const url = this.baseUrl + '/wishlist';
    console.log("!!!!!");
    return this.httpClient.post(url, movieId, httpOptions);
  }
}
