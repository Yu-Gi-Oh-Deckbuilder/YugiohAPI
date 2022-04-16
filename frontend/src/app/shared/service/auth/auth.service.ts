import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Store } from '@ngrx/store';
import { AppState } from '../../../app.state';
import { User } from '../../model/user.model';
import { AuthActions, AuthSelectors } from './state';
import { environment } from 'src/environments/environment'
import { Observable } from 'rxjs';

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

   authenticate(username: string, password: string) {
     return this.http.post<User>(`${environment.apiUrl}/login`, {username, password}, {observe: 'response'});
   }

   getUser(): Observable<User | null> {
     return this.store.select(AuthSelectors.selectAuthUser);
   }

   getErrorMessage(): Observable<string | null> {
     return this.store.select(AuthSelectors.selectErrorMessage);
   }

   isAuthenticated(): Observable<boolean> {
     return this.store.select(AuthSelectors.selectIsAuthenticated);
   }

   logout(){
     this.store.dispatch(AuthActions.logout());
   }
}
