import { Component, OnInit, OnChanges } from '@angular/core';
import { Movie } from '../../common/movie';
import { MovieService } from '../../services/movie.service';
import { GenreService } from 'src/app/services/genre.service';
import { Genre } from 'src/app/common/genre';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-movie-list',
  templateUrl: './movie-list.component.html',
  styleUrls: ['./movie-list.component.css']
})
export class MovieListComponent implements OnInit {

  movies: Movie[];
  amountOfElements: number;
  selectedOrder = 'id ASC';
  selectedGenreId = null;

  pageNumber = 1;
  pageSize = 12;
  
  genres: Genre[];

  constructor(private movieService: MovieService,
              private genreService: GenreService,
              private route: ActivatedRoute) {
                this.route.params.subscribe(params => {
                  this.ngOnInit();
                });
               }

  ngOnInit(): void {
    this.loadListOfMovies();
    this.loadListOfGenres();
  }


  loadListOfMovies(){
    const categoryId: number = +this.route.snapshot.paramMap.get('categoryId');
    const pageNumber = this.pageNumber - 1;
    this.movieService.getMovieList(pageNumber, this.selectedOrder,
        categoryId, this.selectedGenreId).subscribe(
      data => {
        this.movies = data["movies"];
        this.amountOfElements = data["amountOfElements"];
        console.log(this.movies);
      }
    );
    document.body.scrollTop = document.documentElement.scrollTop = 0;
  }

  loadListOfGenres(){
    this.genreService.getGenreList().subscribe(
      data => {
        this.genres = data;
      }
    );
  }


  onButtonGroupClick($event){
    let clickedElement = $event.target || $event.srcElement;
    this.selectedOrder = clickedElement.value;
    if (clickedElement.nodeName === "BUTTON" ) {

      let isCertainButtonAlreadyActive = clickedElement.parentElement.querySelector(".active");
      // if a Button already has Class: .active
      if( isCertainButtonAlreadyActive ) {
        isCertainButtonAlreadyActive.classList.remove("active");
      }

      clickedElement.className += " active";
      this.loadListOfMovies();
    }
  }

  onButtonGenreClick($event){
    let clickedElement = $event.target || $event.srcElement;
    this.selectedGenreId = clickedElement.value;
    if (clickedElement.nodeName === "BUTTON" ) {

      let isCertainButtonAlreadyActive = clickedElement.parentElement.querySelector(".active");
      // if a Button already has Class: .active
      if( isCertainButtonAlreadyActive ) {
        isCertainButtonAlreadyActive.classList.remove("active");
      }
      clickedElement.className += " active";
      this.loadListOfMovies();
    }
  }

  doSearch(value: string){
    this.movieService.getMoviesByTitle(value).subscribe(
      data => {
        this.movies = data;
        this.amountOfElements = 1;
      }
    );
    document.body.scrollTop = document.documentElement.scrollTop = 0;
  }
}
