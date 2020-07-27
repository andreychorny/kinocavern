import { Component, OnInit, Input, ChangeDetectionStrategy } from '@angular/core';
import { GenreService } from '../../../services/genre.service';
import { Genre } from '../../../common/genre';

@Component({
  selector: 'app-attach-genres',
  templateUrl: './attach-genres.component.html',
  styleUrls: ['./attach-genres.component.css']
})
export class AttachGenresComponent implements OnInit {

  @Input()
  movieGenres?: Genre[];

  genres: Genre[];

  checkedGenres: Genre[] = [];
  checkedGenresIds: number[] = [];

  private baseUrl = 'http://localhost:8080/api/genres';

  constructor(private genreService: GenreService) { }

  ngOnInit(): void {
    this.loadListOfGenres();
  }

  loadListOfGenres(){
    this.genreService.getGenreList().subscribe(
      data => {
        this.genres = data;
        if (this.movieGenres != null){ this.setCheckedGenres(); }
      }
    );
  }


  onCheck(checkedGenre) {
    if (!this.checkedGenresIds.includes(checkedGenre.id)) {
      this.checkedGenres.push(checkedGenre);
      this.checkedGenresIds.push(checkedGenre.id);
    } else {
      const indexId = this.checkedGenresIds.indexOf(checkedGenre.id);
      const index = this.checkedGenres.indexOf(checkedGenre);
      if (indexId > -1) {
        this.checkedGenresIds.splice(indexId, 1);
        this.checkedGenres.splice(index, 1);
      }
    }
    console.log(this.checkedGenres);
  }

  setCheckedGenres(){
    for (const genre of this.genres){
      this.isGenreInMovie(genre);
    }
  }

  public isGenreInMovie(genre: Genre): boolean {
    const genreExistInMovie: boolean = this.movieGenres.some(obj => obj.id === genre.id);
    if (genreExistInMovie) {
      this.onCheck(genre)}
    return genreExistInMovie;
  }

}
