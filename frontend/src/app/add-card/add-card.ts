import { Component,Inject, ElementRef, ViewChild } from "@angular/core";
import { MatDialogRef, MAT_DIALOG_DATA } from "@angular/material/dialog";
import { Observable } from "rxjs";
import { Card } from "../shared/model/card.model";
import { Wishlist } from "../shared/model/wishlist.model";
import { CardService } from "../shared/service/card/card.service";
import { WishlistService } from "../shared/service/wishlist/wishlist.service";
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {FormControl} from '@angular/forms';
import {MatAutocompleteSelectedEvent} from '@angular/material/autocomplete';
import {MatChipInputEvent} from '@angular/material/chips';;
import {map, startWith} from 'rxjs/operators';

@Component({
    selector: 'add-card',
    templateUrl: 'add-card.html',
    styleUrls: ['add-card.scss']
})
export class AddCardDialog {

    
    wishlist!:any;

    separatorKeysCodes: number[] = [ENTER, COMMA];
    cardCtrl = new FormControl();
    //filteredCards: Observable<string[]>;
    cards: string[] = [];
    allCards: Card[] =[];
    cardToAdd!:Card;
    cardsFromApi:Observable<Card[]>;
    @ViewChild('cardInput') cardInput!: ElementRef<HTMLInputElement>;

    constructor(
    public dialogRef: MatDialogRef<AddCardDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Wishlist,
    private wishlistService:WishlistService,
    private cardService:CardService,
    ){
        this.wishlist = data;

        this.cardsFromApi= this.cardService.selectAllCards();

        this.cardsFromApi.
        subscribe(
            {
                next:cards=>{
                    this.allCards = cards;
                },
                error:err=>console.error(err)
            }
        )        
    }

    ngOnInit(): void {
    }

    setCard(card:Card){
        this.cardToAdd = card;
    }

    onCancel() {
        this.dialogRef.close();
    }

    add(event: MatChipInputEvent): void {
        const value = (event.value);
    
        // Add our card
        if (value) {
            this.cards.push(value);
        }
    
        // Clear the input value
        event.chipInput!.clear();
    
        this.cardCtrl.setValue(null);
    }

    remove(cardName: string): void {
        const index = this.cards.indexOf(cardName);
    
        if (index >= 0) {
            this.cards.splice(index, 1);
        }
    }

    selected(event: MatAutocompleteSelectedEvent): void {
        this.cards.push(event.option.viewValue);
        this.cardInput.nativeElement.value = '';
        this.cardCtrl.setValue(null);
    }
}