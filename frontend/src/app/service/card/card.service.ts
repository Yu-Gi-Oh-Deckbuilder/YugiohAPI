import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiData } from '../../model/apiData';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  private yugiohApiUrl = 'https://db.ygoprodeck.com/api/v7/cardinfo.php';
  
  constructor(private http: HttpClient) { }

  getAllCardsFromApi():Observable<ApiData>{
    return this.http.get<any>(this.yugiohApiUrl);
  }
}
