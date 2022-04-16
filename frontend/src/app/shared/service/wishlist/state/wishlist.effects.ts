import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of, tap } from "rxjs";
import { WishlistService } from "../wishlist.service";
import * as fromActions from "./wishlist.actions";

@Injectable()
export class AuthEffects {

  constructor(
    private actions$: Actions,
    private wishlistService: WishlistService,
    private router: Router
  )
  {}
/*
  getWishlistsByUserId$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.getWishlistsByUserId),
      mergeMap(data =>
        this.wishlistService.getWishlistsByUserId(data).pipe(
          map(response =>{
            const wishlists = response;
            return fromActions.retrievedWishlistsByUserId(wishlists);
          }),
          catchError(err => {
            return of(fromActions.loginFail({message: err.error}))
          })
        )
      )
    )
  );
*/
}    