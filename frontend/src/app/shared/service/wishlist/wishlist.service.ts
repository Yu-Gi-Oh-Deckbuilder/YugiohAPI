import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { AppState } from '../../../app.state';
import { Wishlist } from '../../model/wishlist.model';
import { Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  
  private wishlistsUrl = `http://localhost:9011/collections/users/${2}/wishlists`;
  constructor(private store: Store<AppState>,private http: HttpClient) { }

  //TODO: Get all wishlists by userId 
  getWishlistsByUserId(userId:number):Observable<Wishlist[]>{
    return this.http.get<Wishlist[]>(this.wishlistsUrl);
  }
  
  //TODO: Get wishlist by wishlistId 
  getWishlistsById(wishlistId:number):Observable<Wishlist>{
    return this.http.get<Wishlist>(this.wishlistsUrl+`/${wishlistId}`);
  }

  //TODO: Create wishlist 
  //TODO: Edit wishlist  
  //TODO: Delete wishlist 
}
