import { Component, OnInit } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  user;

  pageOfRatedMovies: Array<any>;
  pageOfWishlistedMovies: Array<any>;

  constructor(private userService: UserService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.handleUserProfile();
  }

  handleUserProfile() {

    // get the "id" param string. convert string to a number using the "+" symbol
    const userId: number = +this.route.snapshot.paramMap.get('id');

    this.userService.getUser(userId).subscribe(
      data => {
        this.user = data;
        console.log(data);
      }
    );
  }

  onChangeRatePage(pageOfItems: Array<any>) {
    // update current page of items
    this.pageOfRatedMovies = pageOfItems;
  }

  onChangeWishlistPage(pageOfItems: Array<any>) {
    // update current page of items
    this.pageOfWishlistedMovies = pageOfItems;
  }
}
