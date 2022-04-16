import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { AppState } from '../../../app.state';
@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  constructor(private store: Store<AppState>,private http: HttpClient) { }

  //TODO: Get all wishlists by userId 
  getWishlistsByUserId(){

  }
  //TODO: Get wishlist by wishlistId 
  //TODO: Create wishlist 
  //TODO: Edit wishlist  
  //TODO: Delete wishlist 
}
