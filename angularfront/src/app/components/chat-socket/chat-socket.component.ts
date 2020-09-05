import { Component, OnInit, Input, OnChanges } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Message } from '../../common/message';
import { SocketService } from 'src/app/services/socket.service';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';
import { ToastrService } from 'ngx-toastr';
import { TokenStorageService } from '../../services/token-storage.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-chat-socket',
  templateUrl: './chat-socket.component.html',
  styleUrls: ['./chat-socket.component.css']
})
export class ChatSocketComponent implements OnChanges {

  private serverUrl = 'http://localhost:8080/socket';
  isCustomSocketOpened = false;
  private stompClient;
  form: FormGroup;
  userForm: FormGroup;
  messages: Message[] = [];

  @Input()
  userToId: number;

  constructor(private socketService: SocketService, private toastr: ToastrService,
              private tokenStorage: TokenStorageService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.form = new FormGroup({
      message: new FormControl(null, [Validators.required])
    });
    this.initializeWebSocketConnection();
  }

  ngOnChanges() {
    this.retrieveChatHistory();
  }

  retrieveChatHistory(){
    console.log(this.userToId);
    this.socketService.getChatHistory(this.userToId).subscribe(
      data => {
        console.log(data);
        this.messages = data;
      }
    );
  }

  sendMessageUsingRest() {
    if (this.form.valid) {
      let message: string = this.form.value.message;
      this.socketService.post(message, this.userToId).subscribe(res => {
        
      });
    }
  }

  initializeWebSocketConnection(){
    let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, function (frame) {
      that.openUserSocket();
    });

  }

  openUserSocket() {
    this.isCustomSocketOpened = true;
    console.log('try to open');
    this.stompClient.subscribe("/socket-publisher/"+ this.tokenStorage.getUser().id,
                                                            (message) => {
                                    console.log('YES' + message);
                                    this.handleResult(message);
    });
  }

  handleResult(message){
    if (message.body) {
      let messageResult: Message = JSON.parse(message.body);
      console.log(messageResult);
      this.messages.push(messageResult);
      console.log('IN HANDLE RESULT');
      this.toastr.success("new message recieved", null, {
        'timeOut': 3000
      });
    }
  }

  isMessageBelongsToCurrentUser(message: Message): boolean{
    if (message.fromId == this.userToId.toString()){
      return false;
    }
    return true;
  }

}
