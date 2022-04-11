import { Injectable } from "@angular/core";
import { Actions, createEffect, ofType } from "@ngrx/effects";
import { map, mergeMap } from "rxjs";
import { AuthService } from "../auth.service";
import * as fromActions from "./auth.actions";

@Injectable()
export class AuthEffects {

  constructor(
    private actions$: Actions,
    private auth: AuthService,
  )
  {}

}
