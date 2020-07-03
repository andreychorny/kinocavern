import { Component, OnInit } from '@angular/core';
import { Movie } from '../../common/movie';
import { MovieService } from '../../services/movie.service';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  movies: Movie[];

  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    this.listOfMovies();
  }

  listOfMovies(){
    this.movieService.getMovieList().subscribe(
      data => {
        this.movies = data;
      }
    );
  }
}
