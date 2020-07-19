import { Component, OnInit } from '@angular/core';
import { MovieService } from '../../../services/movie.service';
import { Movie } from '../../../common/movie';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent implements OnInit {

  movie: Movie = new Movie();
  isSuccessful = false;
  isPostFailed = false;
  errorMessage = '';

  currentFile: File;


  constructor(private movieService: MovieService) { }

  ngOnInit() {
  }

  selectFile(event) {
    this.currentFile = event.target.files.item(0);
  }

  onSubmit() {
    this.movieService.postMovie(this.movie, this.currentFile).subscribe(
      data => {
        console.log(data);
        this.isSuccessful = true;
        this.isPostFailed = false;
      },
      err => {
        this.errorMessage = err.error.message;
        this.isPostFailed = true;
      }
    );
  }

}
