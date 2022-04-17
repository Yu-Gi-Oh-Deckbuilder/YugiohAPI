import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from 'src/app/app.state';
import { ApiData } from '../../model/api-data.model';
import { CardActions } from './state';

@Injectable({
  providedIn: 'root'
})
export class CardService {

  private yugiohApiUrl = 'https://db.ygoprodeck.com/api/v7/cardinfo.php';

  constructor(
    private http: HttpClient,
    private store: Store<AppState>
    ) { }

  loadCards() {
    this.store.dispatch(CardActions.loadCards());
  }

  getAllCardsFromApi():Observable<ApiData>{
    return this.http.get<any>(this.yugiohApiUrl);
  }
}