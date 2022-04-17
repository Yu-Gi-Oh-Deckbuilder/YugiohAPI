import { Component, OnInit } from '@angular/core';
import { User } from '../shared/model/user.model';
import { Wishlist } from '../shared/model/wishlist.model';
import { WishlistService } from '../shared/service/wishlist/wishlist.service';
import { Router } from "@angular/router";
import { AuthService } from '../shared/service/auth/auth.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-wishlists',
  templateUrl: './wishlists.component.html',
  styleUrls: ['./wishlists.component.scss']
})
export class WishlistsComponent implements OnInit {

  wishlists$:Observable<Wishlist[]>;
  //wishlists: Wishlist[] = [];
  owner!: User;
  displayedColumns: string[] = ['name','cards', 'sharedUsers'];
  dataSource:  Wishlist[] = [];
  numOfCards:number=0;
  constructor(private wishlistService: WishlistService,
    private router: Router,
    private authService: AuthService) { 
      this.getUser();
      this.wishlistService.getUsersWishlists(this.owner.id);
      this.wishlists$ = this.wishlistService.selectWishlists();
    }

  ngOnInit(): void {
    this.wishlists$
    .subscribe(
      { 
        next:wishlists=>{
          this.dataSource= wishlists;
        },
        error:err=>{console.log(err)}
      }
    )
  }

  getUser(){
    this.authService.getUser()
    .subscribe(
      {
        next:user => this.owner=user!,
        error:err=>console.log(err)
      }
    )
  }

  getWishlistsByUserId(userId:number){
    this.wishlistService.getUsersWishlists(userId);
  }

  getWishlistById(wishlist:Wishlist){
    this.router.navigate([`/wishlists/${wishlist.id}`]);
  }


}
