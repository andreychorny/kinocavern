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

  getUser(userId: number): Observable<any> {
    const userUrl = `${this.baseUrl}/${userId}`;
    return this.httpClient.get<any>(userUrl);
  }

  postRating(movieId: number, rate: number): Observable<any>{
    const url = this.baseUrl + '/rate';
    const rating = {
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

  postComment(movieId: number, content: string, commentParentId: number): Observable<any>{
    const url = this.baseUrl + '/comment';
    const comment = {
      movieId,
      content,
      commentParentId
    };
    return this.httpClient.post(url, comment, httpOptions);
  }

}
