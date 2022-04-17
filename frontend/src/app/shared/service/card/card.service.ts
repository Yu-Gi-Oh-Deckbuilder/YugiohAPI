import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { Observable } from 'rxjs';
import { AppState } from 'src/app/app.state';
import { ApiData } from '../../model/api-data.model';
import { CardActions, CardSelectors } from './state';

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

  selectCardByName(name: string) {
    return this.store.select(CardSelectors.selectCardByName(name));
  }

  selectCardById(cardId: number) {
    return this.store.select(CardSelectors.selectCardById(cardId));
  }

  selectCardByIdMap(cardId: number) {
    return this.store.select(CardSelectors.selectCardByIdMap(cardId));
  selectAllCards() {
    return this.store.select(CardSelectors.selectAllCards);
  }

  selectPaginatedCards(start:number, end: number) {
    return this.store.select(CardSelectors.selectPaginatedCards(start, end));
  }

  selectTotalCards() {
    return this.store.select(CardSelectors.selectTotalCards);
  }

  selectFilteredCards(filter: string | number) {
    return this.store.select(CardSelectors.selectFilteredCards(filter));
  }

  selectFilteredAndPaginatedCards(filter: string | number, start: number, end: number) {
    return this.store.select(CardSelectors.selectFilteredAndPaginatedCards(filter, start, end));
  }

  selectFilteredTotal(filter: string | number) {
    return this.store.select(CardSelectors.selectFilteredTotal(filter));
  }
}
