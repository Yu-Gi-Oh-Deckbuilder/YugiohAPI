import { Component, OnInit } from '@angular/core';
import { User } from '../shared/model/user.model';
import { Wishlist } from '../shared/model/wishlist.model';
import { WishlistService } from '../shared/service/wishlist/wishlist.service';
import { Router } from "@angular/router";

@Component({
  selector: 'app-wishlists',
  templateUrl: './wishlists.component.html',
  styleUrls: ['./wishlists.component.scss']
})
export class WishlistsComponent implements OnInit {

  wishlists: Wishlist[] = [];
  owner?: User;
  displayedColumns: string[] = ['name','cards', 'sharedUsers'];
  dataSource:  Wishlist[] = [];
  constructor(private wishlistService: WishlistService,private router: Router) { }

  ngOnInit(): void {
    this.getWishlistsByUserId(2);
  }

  getWishlistsByUserId(userId:number){
    this.wishlistService.getWishlistsByUserId(userId)
    .subscribe(wishlists=>{
      wishlists.forEach(wishlist=>{
        this.wishlists.push(wishlist);
      });

      this.owner = wishlists[0].owner;
      this.dataSource = this.wishlists;
      console.log(wishlists);
    })
  }

  getWishlistById(wishlist:Wishlist){
    this.router.navigate([`/wishlists/${wishlist.id}`]);
  }


}
