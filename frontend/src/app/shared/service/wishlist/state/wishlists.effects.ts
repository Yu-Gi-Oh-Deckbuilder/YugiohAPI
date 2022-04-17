import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of, tap } from "rxjs";
import { WishlistService } from "../wishlist.service";
import * as wishlistsActions from "./wishlists.actions";

@Injectable()
export class WishlistsEffects {

  constructor(
    private actions$: Actions,
    private wishlistService: WishlistService,
    private router: Router
  )
  {}

  getWishlistsByUserId$ = createEffect(() =>
    this.actions$.pipe(
      ofType(wishlistsActions.getWishlistsByUserId),
      mergeMap(data =>
        this.wishlistService.getWishlistsByUserId(data.userId).pipe(
          map(response =>{
            const wishlists = response.body;
            return wishlistsActions.retrievedWishlistsByUserId({wishlists:wishlists!});
          }),
          // catchError(err => {
          //   return of(wishlistsActions.loginFail({message: err.error}))
          // })
        )
      )
    )
  );


// getWishlistById$ = createEffect(()=>
//   this.actions$.pipe(
//     ofType(fromActions.getWishlistById),
//     mergeMap(data=>
//       this.wishlistService.getWishlistsById(data).pipe(
//         map(wishlist=>{
          
//         })
//       )
//     )
//   ))
}    