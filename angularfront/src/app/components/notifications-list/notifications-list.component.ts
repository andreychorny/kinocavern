import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../../services/notification.service';

@Component({
  selector: 'app-notifications-list',
  templateUrl: './notifications-list.component.html',
  styleUrls: ['./notifications-list.component.css']
})
export class NotificationsListComponent implements OnInit {

  notifications;

  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.getNotificationsList();
  }

  getNotificationsList(){
    this.notificationService.getNotifications().subscribe(
      data => {
        this.notifications = data;
        console.log(this.notifications);
      }
    );
  }
}
