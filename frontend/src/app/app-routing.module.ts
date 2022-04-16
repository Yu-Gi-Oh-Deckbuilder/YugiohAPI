import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { WishlistComponent } from './wishlist/wishlist.component';

const routes: Routes = [
  {path:'home', component:HomepageComponent},
  {path:'wishlists',component:WishlistComponent},
  {path:'', redirectTo:'home',pathMatch:'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
