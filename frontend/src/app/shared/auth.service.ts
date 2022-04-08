import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../app.state';
import { User } from './model/user.model';
import { AuthActions } from './state';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(
    private store: Store<AppState>,
    private http: HttpClient
    ) {

   }

   login(username: string, password: string) {
     this.store.dispatch(AuthActions.login({username: username, password: password}));
   }

}
