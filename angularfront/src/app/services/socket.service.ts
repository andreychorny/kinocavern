import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Message } from '../common/message';
import { Observable, throwError } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class SocketService {

  url: string = "http://localhost:8080/api/chats";

  constructor(private http: HttpClient) { }

  getChats(){
    return this.http.get(this.url);
  }

  getChatHistory(userToId: number){
    const getUrl = this.url +'/' +userToId;
    return this.http.get(getUrl).pipe(map(
      (data: any) => {
        data = data.map(chatUnit => {
          const id = chatUnit.id;
          const fromId = chatUnit.userFrom.id;
          const toId = chatUnit.userTo.id;
          const message = chatUnit.message;
          const usernameFrom = chatUnit.userFrom.username;
          const usernameTo = chatUnit.userTo.username;
          return {id, fromId, toId, message, usernameFrom, usernameTo};
        });
        data.sort((a,b) => (a.id > b.id) ? 1 : ((b.id > a.id) ? -1 : 0));
        return data;
        }
      )
      );
  }

  post(data: string, userToId: number) {
    const postUrl = this.url  + '/' + userToId;
    return this.http.post(postUrl, data);
  }
}
