import { Component, OnInit } from '@angular/core';
import { TokenStorageService } from '../../services/token-storage.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public tokenStorageService: TokenStorageService) { }

  ngOnInit(): void {
  }

  getUserProfile(): string{
      const id = this.tokenStorageService.getUser().id;
      const profileUrl = '/users/' + id;
      return profileUrl;
  }
}
