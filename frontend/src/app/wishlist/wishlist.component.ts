
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Wishlist } from '../shared/model/wishlist.model';
import { WishlistService } from '../shared/service/wishlist/wishlist.service';
import { CardAmount } from '../shared/model/cardamount.model';

@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.scss']
})


export class WishlistComponent implements OnInit {
  
  wishlist?:Wishlist;
  cards:CardAmount[]=[];
  displayedColumns: string[] = ['cardId','cardAmount'];
  dataSource:CardAmount[] = [];

  constructor(private route: ActivatedRoute, private wishlistService: WishlistService) { }

  ngOnInit(): void {
    this.getWishlist();
  }

  getWishlist(){
    const id = Number(this.route.snapshot.paramMap.get('id'))
    this.wishlistService.getWishlistsById(id)
    .subscribe(wishlist=>{
      this.wishlist=wishlist;
      this.wishlist.cards.forEach(card=>{
        this.cards?.push(card);
      })
      this.dataSource=this.cards;    
    });
  }
 
}
