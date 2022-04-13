import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  private yugiohApiUrl = 'https://db.ygoprodeck.com/api/v7/cardinfo.php';

  private cards:any = [];
  
  constructor(private http: HttpClient) { }

  getAllCardsFromApi(){
    
  }
}
