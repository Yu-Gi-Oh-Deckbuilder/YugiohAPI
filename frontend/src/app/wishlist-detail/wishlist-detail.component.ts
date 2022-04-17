import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Card } from '../shared/model/card.model';
import { CardAmount } from '../shared/model/cardamount.model';
import { Wishlist } from '../shared/model/wishlist.model';
import { CardService } from '../shared/service/card/card.service';

import { WishlistService } from '../shared/service/wishlist/wishlist.service';

@Component({
  selector: 'app-wishlist-detail',
  templateUrl: './wishlist-detail.component.html',
  styleUrls: ['./wishlist-detail.component.scss']
})
export class WishlistDetailComponent implements OnInit {

  wishlist!:Wishlist;
  displayedColumns: string[] = ['cardId','cardName'];
  dataSource:Card[] =[];

  constructor(private route: ActivatedRoute,
    private wishlistService: WishlistService,
    private cardService: CardService) { }

  ngOnInit(): void {
    this.getWishlist();
  }

  getWishlist(){
    const id = Number(this.route.snapshot.paramMap.get('id'))
    this.wishlistService.getWishlistsById(id)
    .subscribe(wishlist=>{
      console.log(wishlist);
      this.wishlist = wishlist
      this.wishlist.cards.forEach(card=>{
        console.log(card.cardId); 
        this.cardService.selectCardByIdMap(card.cardId)
        .subscribe(
          {
            next: card =>this.dataSource.push(card!),
            error:err=>console.log(err)
          }
        );
      })  
    });
  }
}
