import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Store } from '@ngrx/store';
import { AppState } from '../../../app.state';
import { Wishlist } from '../../model/wishlist.model';
import { Observable } from 'rxjs';
import * as WishlistsActions from './state/wishlists.actions'
import { WishlistsSelectors } from './state';
@Injectable({
  providedIn: 'root'
})
export class WishlistService {

  
  private wishlistsUrl = `http://localhost:9011/collections/users/${2}/wishlists`;
  constructor(private store: Store<AppState>,private http: HttpClient) { }

  //TODO: Get all wishlists by userId 

  getUsersWishlists(userId:number){
    this.store.dispatch(WishlistsActions.getWishlistsByUserId({userId}))
  }

  getWishlistsByUserId(userId:number):Observable<HttpResponse<Wishlist[]>>{
    return this.http.get<Wishlist[]>(this.wishlistsUrl,{observe:'response'});
  }

  selectWishlists(){
    return this.store.select(WishlistsSelectors.selectWishlists)
  }
  
  //TODO: Get wishlist by wishlistId 
  getWishlistsById(wishlistId:number):Observable<Wishlist>{
    return this.http.get<Wishlist>(this.wishlistsUrl+`/${wishlistId}`);
  }

  // getWishlist(wishlistId:number){
  //   this.store.dispatch
  // }

  //TODO: Create wishlist 
  //TODO: Edit wishlist  
  //TODO: Delete wishlist 
}
