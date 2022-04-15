import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/shared/model/user.model';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userUrl = 'http://localhost:9011/users';
  
  constructor(private http:HttpClient) { }

  signup(user:User):Observable<User>{
    return this.http.post<User>(this.userUrl,user);
  }
}
