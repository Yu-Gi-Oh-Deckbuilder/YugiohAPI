import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Card } from '../shared/model/card.model';
import { CardAmount } from '../shared/model/cardamount.model';
import { Wishlist } from '../shared/model/wishlist.model';
import { CardService } from '../shared/service/card/card.service';

import {MatDialog} from '@angular/material/dialog';
import { WishlistService } from '../shared/service/wishlist/wishlist.service';
import { AddCardDialog } from '../add-card/add-card';

@Component({
  selector: 'app-wishlist-detail',
  templateUrl: './wishlist-detail.component.html',
  styleUrls: ['./wishlist-detail.component.scss']
})
export class WishlistDetailComponent implements OnInit {

  wishlist$:Observable<Wishlist|undefined>;
  wishlist!:Wishlist;
  displayedColumns: string[] = ['card','cardId','cardName'];
  dataSource:Card[] = [];

  cardToAdd!:Card;

  cardsToAdd:CardAmount[] = [];

  cardAmountToAdd:CardAmount = {id:0,cardId:0,cardAmount:1};
  
  constructor(private route: ActivatedRoute,
    private wishlistService: WishlistService,
    private cardService: CardService,
    private dialog: MatDialog) { 
      const id = Number(this.route.snapshot.paramMap.get('id'));
      this.wishlist$ = this.wishlistService.getWishlistById(id);
      console.log(this.wishlist$);
    }

  ngOnInit(): void {
    this.wishlist$
    .subscribe(
      {
        next:wishlist=>{
          this.wishlist= wishlist!;
          this.wishlist.cards.forEach(card=>{
            this.cardService.selectCardById(card.cardId)
            .subscribe(
              {
                next:card=>this.dataSource.push(card!),
                error:err=>console.log(err)
              }
            )
          })
        },
        error:err=>{console.log(err)}
      }
    )
  }

  openDialog(){
    const dialogRef = this.dialog.open(AddCardDialog, {data: this.wishlist});
    dialogRef.afterClosed().subscribe(
      {
        next:card => {


          this.cardToAdd = card;
          
          //use strings to find cards
          // cards.forEach((cardName: string)=>{
          //   this.cardToAdd = this.cardService.selectCardByName(cardName)
          //   this.cardToAdd.subscribe(
          //     {
          //       next:card=>{
          //         if(card){
          //           console.log(this.cardAmountToAdd);
          //           this.cardAmountToAdd.cardId=card.id;
          //           this.cardAmountToAdd.cardAmount=1;
          //           this.cardsToAdd.push(this.cardAmountToAdd);
          //         }
          //       }
          //     }
          //   )            
          // })
          //make http call to save card amounts for wishlists
          // this.cardsToAdd.forEach(cardAmount=>{
          // this.wishlist.cards.push(cardAmount);
          // })
          //this.wishlistService.editWishlist(this.wishlist);
          console.log('cardtoBeAdded');
          console.log(this.cardToAdd);
          this.wishlist.cards.push(new CardAmount(0,this.cardToAdd.id,1));
          this.wishlistService.editWishlist(this.wishlist);
          }
          
      }
    );
  }
}

