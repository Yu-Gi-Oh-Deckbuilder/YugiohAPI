import { Component, OnInit } from '@angular/core';
import { SpellTrapCard } from '../model/spelltrapcard';
import { CardService } from '../service/card.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent implements OnInit {

  cards = new Map();

  map = new Map();
  constructor(private cardService:CardService) { }

  ngOnInit(): void {
  }

 
}
