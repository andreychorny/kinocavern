import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  private baseUrl = 'http://localhost:8080/api/comments';

  constructor(private httpClient: HttpClient) { }

  deleteComment(theCommentId: number): Observable<any>{
    const commentUrl = `${this.baseUrl}/${theCommentId}`;
    console.log(commentUrl);
    return this.httpClient.delete(commentUrl);
  }
}
