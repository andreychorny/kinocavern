import { Component, OnInit, OnDestroy } from '@angular/core';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { TokenStorageService } from '../../services/token-storage.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit, OnDestroy {

  user;

  userSubscription: Subscription;

  pageOfRatedMovies: Array<any>;
  pageOfWishlistedMovies: Array<any>;

  constructor(private userService: UserService,
              private route: ActivatedRoute, private tokenStorage: TokenStorageService) { }

  ngOnInit(): void {
    this.userSubscription = this.route.params.subscribe(
      params => {
        this.handleUserProfile();
    });

  }

  ngOnDestroy(): void {
    this.userSubscription.unsubscribe()
  }

  isPageBelongsToLoggedUser(): boolean{
    return (this.tokenStorage.getUser().id === this.user.id);
  }

  isCurrentUserAlreadySubscribed(): boolean{
    const currentUserId = this.tokenStorage.getUser().id;
    console.log(this.user.subscribers);
    const alreadySubscribed: boolean = this.user.subscribers.filter(function(subscriber) {
      console.log(subscriber.id);
      return (subscriber.id === currentUserId);
    }).length > 0;
    console.log(alreadySubscribed);
    return alreadySubscribed;
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

  onButtonSubscribeClick(){
    this.userService.postAddToSubscription(this.user.id).subscribe(
      data => {
        window.location.reload();
      }
    );
  }
}
