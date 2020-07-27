import { Component, OnInit, ViewChild } from '@angular/core';
import { Movie } from '../../../common/movie';
import { MovieService } from '../../../services/movie.service';
import { ActivatedRoute } from '@angular/router';
import { AttachGenresComponent } from '../attach-genres/attach-genres.component';
import { AttachCountriesComponent } from '../attach-countries/attach-countries.component';
import { Category } from 'src/app/common/category';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-edit-movie',
  templateUrl: './edit-movie.component.html',
  styleUrls: ['./edit-movie.component.css']
})
export class EditMovieComponent implements OnInit {

  movie;
  isSuccessful = false;
  isPostFailed = false;
  errorMessage = '';

  categories: Category[];
  selectedCategory: Category;
  currentFile: File;

  @ViewChild(AttachGenresComponent)
  attachGenresComponent: AttachGenresComponent;

  @ViewChild(AttachCountriesComponent)
  attachCountriesComponent: AttachCountriesComponent;

  constructor(private movieService: MovieService,
              private route: ActivatedRoute,
              private categoryService: CategoryService) { }

  ngOnInit(): void {
    this.handleMovieDetails();
  }

  handleMovieDetails() {

    // get the "id" param string. convert string to a number using the "+" symbol
    const theMovieId: number = +this.route.snapshot.paramMap.get('id');
    this.movieService.getMovie(theMovieId).subscribe(
      data => {
        this.movie = data;
        this.loadListOfCategories();
      }
    );

  }

  onSubmit() {
    const genres = this.attachGenresComponent.checkedGenres;
    const countries = this.attachCountriesComponent.checkedCountries;
    this.movieService.updateMovie(this.movie, this.selectedCategory, genres, countries).subscribe(
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

  loadListOfCategories(){
    console.log('loadList');
    this.categoryService.getCategoriesList().subscribe(
      data => {
        this.categories = data;
        for(const categ of this.categories){
          if(categ.id === this.movie.category.id) {
            this.selectedCategory = categ;
          }
        }
      }
    );
  }
}
