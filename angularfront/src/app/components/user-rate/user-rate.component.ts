import { Component, OnInit, Input } from '@angular/core';
import { UserService } from '../../services/user.service';
import { Movie } from '../../common/movie';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-rate',
  templateUrl: './user-rate.component.html',
  styleUrls: ['./user-rate.component.css']
})
export class UserRateComponent implements OnInit {

  @Input()
  movie: Movie;

  selectedRate: null;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onButtonRateClick($event){
    const clickedElement = $event.target || $event.srcElement;
    this.selectedRate = clickedElement.value;
    console.log(this.movie);
    this.userService.postRating(this.movie.id, this.selectedRate).subscribe(
        () => {
            window.location.reload();
        }
    );
    }

}
