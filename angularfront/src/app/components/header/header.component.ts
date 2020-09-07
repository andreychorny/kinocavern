import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../services/token-storage.service';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  private stompClient;

  isNewNotifications: boolean = false;

  constructor(public tokenStorage: TokenStorageService,
     private userService: UserService) { }

  ngOnInit(): void {
    if (this.tokenStorage.getToken()){
      this.checkIfUserHasNewNotifications();
      this.initializeNotificationWebSocketConnection();
    };
  }

  getUserProfile(): string{
    if (this.tokenStorage.getToken()){
      const id = this.tokenStorage.getUser().id;
      const profileUrl = '/users/' + id;
      return profileUrl;
    }
  }

  initializeNotificationWebSocketConnection(){
    const serverUrl = 'http://localhost:8080/socket';
    let ws = new SockJS(serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    
    this.stompClient.connect({}, function (frame) {
      that.openNotificationSocket();
    });

  }

  checkIfUserHasNewNotifications(){
    this.userService.getIfNewNotificationPresent().subscribe(
      data =>{
        this.isNewNotifications = data;
        console.log(this.isNewNotifications);
      }
    );
  }

  openNotificationSocket() {
    this.stompClient.subscribe('/notification-publisher/' +
            this.tokenStorage.getUser().id, (notification) => {
                            this.isNewNotifications = true;
    });
  }

  notificationsViewed(){
    this.isNewNotifications = false;
  }
}
