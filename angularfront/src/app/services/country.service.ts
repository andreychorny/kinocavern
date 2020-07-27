import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Country } from '../common/country';

@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private baseUrl = 'http://localhost:8080/api/countries';

  constructor(private httpClient: HttpClient) { }

  getCountriesList(): Observable<Country[]> {
    return this.httpClient.get<Country[]>(this.baseUrl);
  }
}
