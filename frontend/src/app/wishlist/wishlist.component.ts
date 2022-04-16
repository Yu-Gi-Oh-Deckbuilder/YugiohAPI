import { Component, OnInit } from '@angular/core';
import { User } from '../shared/model/user.model';
import { Wishlist } from '../shared/model/wishlist.model';
import { WishlistService } from '../shared/service/wishlist/wishlist.service';
@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {

  wishlists: Wishlist[] = [];

  constructor(private wishlistService: WishlistService) { }

  ngOnInit(): void {
    this.getWishlistsByUserId(2);
  }

  getWishlistsByUserId(userId:number){
    this.wishlistService.getWishlistsByUserId(userId)
    .subscribe(wishlists=>{
      wishlists.forEach(wishlist=>{
        this.wishlists.push(wishlist);
      });

      console.log(this.wishlists);
    })
  }

}
