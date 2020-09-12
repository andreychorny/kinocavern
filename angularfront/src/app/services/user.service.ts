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

  deleteUser(theUserId: number): Observable<any>{
    const userUrl = `${this.baseUrl}/${theUserId}`;
    return this.httpClient.delete(userUrl);
  }

  postRating(movieId: number, rate: number): Observable<any>{
    const url = 'http://localhost:8080/api/rates';
    const rating = {
      movieId,
      rate
    };
    return this.httpClient.post(url, rating, httpOptions);
  }

  postToWishlist(movieId: number): Observable<any>{
    const url = 'http://localhost:8080/api/wishlists';
    return this.httpClient.post(url, movieId, httpOptions);
  }

  postComment(movieId: number, content: string, commentParentId: number): Observable<any>{
    const url = 'http://localhost:8080/api/comments';
    const comment = {
      movieId,
      content,
      commentParentId
    };
    return this.httpClient.post(url, comment, httpOptions);
  }

  postAddToSubscription(userId: number): Observable<any>{
    const url = this.baseUrl + '/subscribeTo/' + userId;
    return this.httpClient.post(url, null, httpOptions);
  }

  getIfNewNotificationPresent(): Observable<boolean>{
    const url =  this.baseUrl + '/newNotifications';
    return this.httpClient.get<boolean>(url);
  }

}
