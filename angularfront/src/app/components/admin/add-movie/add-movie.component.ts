import { Component, OnInit, ViewChild } from '@angular/core';
import { MovieService } from '../../../services/movie.service';
import { Movie } from '../../../common/movie';
import { AttachGenresComponent } from '../attach-genres/attach-genres.component';
import { AttachCountriesComponent } from '../attach-countries/attach-countries.component';
import { CategoryService } from '../../../services/category.service';
import { Category } from 'src/app/common/category';

@Component({
  selector: 'app-add-movie',
  templateUrl: './add-movie.component.html',
  styleUrls: ['./add-movie.component.css']
})
export class AddMovieComponent implements OnInit {

  movie: Movie = new Movie();
  categories: Category[];
  selectedCategoryId: number = 1;

  isSuccessful = false;
  isPostFailed = false;
  errorMessage = '';

  currentFile: File;

  @ViewChild(AttachGenresComponent)
  attachGenresComponent: AttachGenresComponent;

  @ViewChild(AttachCountriesComponent)
  attachCountriesComponent: AttachCountriesComponent;

  constructor(private movieService: MovieService,
              private categoryService: CategoryService) {
  }

  ngOnInit() {
    this.loadListOfCategories();
  }

  selectFile(event) {
    this.currentFile = event.target.files.item(0);
  }

  onSubmit() {
    const genresIds = this.attachGenresComponent.checkedGenresIds;
    const countriesIds = this.attachCountriesComponent.checkedCountriesIds;
    console.log(this.movie.description);
    if(genresIds.length !== 0 && countriesIds.length !== 0){
      this.movieService.postMovie(this.movie, this.currentFile,
        genresIds, countriesIds, this.selectedCategoryId).subscribe(
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
      }else{
        this.errorMessage = 'Genres and countries cannot be empty!';
        this.isPostFailed = true;
      }
    }

    loadListOfCategories(){
      this.categoryService.getCategoriesList().subscribe(
        data => {
          this.categories = data;
        }
      );
    }
}
