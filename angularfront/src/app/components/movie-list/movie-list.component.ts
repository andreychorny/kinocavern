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
  amountOfElements: number;

  pageNumber = 1;
  pageSize = 12;
  constructor(private movieService: MovieService) { }

  ngOnInit(): void {
    this.listOfMovies();
  }

  listOfMovies(){
    const pageNumber = this.pageNumber - 1;
    this.movieService.getMovieList(pageNumber).subscribe(
      data => {
        this.movies = data["movies"];
        this.amountOfElements = data["amountOfElements"];
      }
    );
    document.body.scrollTop = document.documentElement.scrollTop = 0;
  }
}
