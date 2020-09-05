import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { SocketService } from '../../services/socket.service';

@Component({
  selector: 'app-chat-list',
  templateUrl: './chat-list.component.html',
  styleUrls: ['./chat-list.component.css']
})
export class ChatListComponent implements OnInit {

  userIdToChatWith: number;

  usersChats;

  constructor(private route: ActivatedRoute,
              private socketService: SocketService) { }

  ngOnInit(): void {
    this.userIdToChatWith = +this.route.snapshot.paramMap.get('id');
    this.route.params.subscribe(
      params => {
          console.log("!!!");
          this.userIdToChatWith = +params['id'];
          console.log(this.userIdToChatWith);
      }
  );
    this.socketService.getChats().subscribe(
      data => {
        console.log(data);
        this.usersChats = data;
      }
    );
  }

}
