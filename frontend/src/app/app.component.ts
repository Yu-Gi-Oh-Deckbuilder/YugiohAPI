import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { LoginDialog } from './dialog/login.dialog';
import { CardService } from './shared/service/card/card.service';
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


  constructor(
    private auth: AuthService,
    private dialog: MatDialog,
    private cardService: CardService
    ){
      this.isAuthenticated = auth.isAuthenticated();
      this.errorMessage = auth.getErrorMessage();
    }

    ngOnInit(): void {
      this.cardService.loadCards();
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
    this.auth.logout();
  }
}
