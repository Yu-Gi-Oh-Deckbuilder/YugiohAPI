import { Component, OnInit } from '@angular/core';
import { User } from '../shared/model/user.model';
@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})
export class WishlistComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
    //this.getWishlistsByUserId());
  }

  getWishlistsByUserId(userId:number){

  }

}
