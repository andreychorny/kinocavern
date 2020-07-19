import { Component, OnInit } from '@angular/core';
import { Movie } from '../../../common/movie';
import { MovieService } from '../../../services/movie.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-edit-movie',
  templateUrl: './edit-movie.component.html',
  styleUrls: ['./edit-movie.component.css']
})
export class EditMovieComponent implements OnInit {

  movie: Movie = new Movie();
  isSuccessful = false;
  isPostFailed = false;
  errorMessage = '';

  currentFile: File;


  constructor(private movieService: MovieService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handleMovieDetails();
    });
  }

  handleMovieDetails() {

    // get the "id" param string. convert string to a number using the "+" symbol
    const theMovieId: number = +this.route.snapshot.paramMap.get('id');

    this.movieService.getMovie(theMovieId).subscribe(
      data => {
        this.movie = data;
      }
    );
  }

  onSubmit() {
    this.movieService.updateMovie(this.movie).subscribe(
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

  selectFile(event) {
    this.currentFile = event.target.files.item(0);
  }

  onSubmitImg() {
    this.movieService.updateTitleImg(this.currentFile, this.movie.imageUrl).subscribe(
      data => {
        console.log(data);
        this.reloadPage();
      }
    );
  }

   reloadPage() {
    window.location.reload();
  }
}
