import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { LoginDialog } from './dialog/login.dialog';
import { SpellTrapCard } from './model/spelltrapcard';
import { CardService } from './service/card/card.service';
import { AuthService } from './shared/service/auth/auth.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'DeckBuilder';
  isAuthenticated: Observable<boolean>;
  errorMessage: Observable<string | null>;

  cards: Map<number,SpellTrapCard> = new Map();

  constructor(
    private auth: AuthService,
    private dialog: MatDialog,
    private cardService: CardService
    ){
      this.isAuthenticated = auth.isAuthenticated();
      this.errorMessage = auth.getErrorMessage();
    }

    ngOnInit(): void {
      this.getCards();
    }

  login() {
    const dialogRef = this.dialog.open(LoginDialog, {data: {username: "", password: ""}});
    dialogRef.afterClosed().subscribe({
      next: data => {
        this.auth.login(data.username, data.password);
      }
    })
  }

  logout() {
    //TODO
  }

  getCards(){
    this.cardService.getAllCardsFromApi()
    .subscribe((res) => {
      console.log(res.data);
      res.data.forEach(card=>{
        this.cards.set(card.id,card);
      });
    });
  }
}
