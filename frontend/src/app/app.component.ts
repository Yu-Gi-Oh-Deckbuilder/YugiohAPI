import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Observable } from 'rxjs';
import { LoginDialog } from './dialog/login.dialog';
import { AuthService } from './shared/auth.service';

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
    private dialog: MatDialog
    ){
      this.isAuthenticated = auth.isAuthenticated();
      this.errorMessage = auth.getErrorMessage();
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
}
