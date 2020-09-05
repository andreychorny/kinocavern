import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  private baseUrl = 'http://localhost:8080/api/notifications';

  constructor(private httpClient: HttpClient) { }

  getNotifications(): Observable<any> {
    return this.httpClient.get<any>(this.baseUrl);
  }
}
