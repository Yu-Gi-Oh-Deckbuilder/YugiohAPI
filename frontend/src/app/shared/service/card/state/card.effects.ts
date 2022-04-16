import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of } from "rxjs";
import { CardService } from "../card.service";
import * as fromActions from './card.actions';

@Injectable()
export class CardEffects {
  constructor(
    private actions$: Actions,
    private card: CardService,
  )
  {}

  loadCards$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.loadCards),
      mergeMap(() =>
        this.card.getAllCardsFromApi().pipe(
          map(data => fromActions.loadCardsSuccess({ apiData: data })),
          catchError(error => of(fromActions.loadCardsFail({ message: error.error.message })))
        )
      )
    )
  );
}
