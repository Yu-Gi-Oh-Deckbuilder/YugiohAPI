import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { catchError, map, mergeMap, of } from "rxjs";
import { AuthService } from "../auth.service";
import * as fromActions from "./auth.actions";

@Injectable()
export class AuthEffects {

  constructor(
    private actions$: Actions,
    private auth: AuthService,
  )
  {}

  login$ = createEffect(() =>
    this.actions$.pipe(
      ofType(fromActions.login),
      mergeMap(data =>
        this.auth.authenticate(data.username, data.password).pipe(
          map(response =>{
            const token = response.headers.get('Token')!;
            const user = response.body!;
            localStorage.setItem('jwt', token);
            return fromActions.loginSuccess({user: user, token: token});
          }),
          catchError(err => {
            return of(fromActions.loginFail({message: err.error}))
          })
        )
      )
    )
  );
}
