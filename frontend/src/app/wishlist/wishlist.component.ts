
import { Component, OnInit } from '@angular/core';
import { User } from '../shared/model/user.model';
import { Wishlist } from '../shared/model/wishlist.model';
import { UserService } from '../shared/service/user/user.service';
import { WishlistService } from '../shared/service/wishlist/wishlist.service';

export interface PeriodicElement {
  name: string;
  position: number;
  weight: number;
  symbol: string;
}


const ELEMENT_DATA: PeriodicElement[] = [
  {position: 1, name: 'Hydrogen', weight: 1.0079, symbol: 'H'},
  {position: 2, name: 'Helium', weight: 4.0026, symbol: 'He'},
  {position: 3, name: 'Lithium', weight: 6.941, symbol: 'Li'},
  {position: 4, name: 'Beryllium', weight: 9.0122, symbol: 'Be'},
  {position: 5, name: 'Boron', weight: 10.811, symbol: 'B'},
  {position: 6, name: 'Carbon', weight: 12.0107, symbol: 'C'},
  {position: 7, name: 'Nitrogen', weight: 14.0067, symbol: 'N'},
  {position: 8, name: 'Oxygen', weight: 15.9994, symbol: 'O'},
  {position: 9, name: 'Fluorine', weight: 18.9984, symbol: 'F'},
  {position: 10, name: 'Neon', weight: 20.1797, symbol: 'Ne'},
];
@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})


export class WishlistComponent implements OnInit {
  

  wishlists: Wishlist[] = [];
  owner?: User;
  displayedColumns: string[] = ['name','cards', 'sharedUsers'];
  dataSource:  Wishlist[] = [];
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

      this.owner = wishlists[0].owner;
      this.dataSource = this.wishlists;
      console.log(wishlists);
    })
  }

  getWishlistById(wishlist:Wishlist){
    console.log(wishlist.id);
  }

}
