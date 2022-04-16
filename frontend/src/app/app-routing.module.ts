import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomepageComponent } from './homepage/homepage.component';
import { WishlistComponent } from './wishlist/wishlist.component';
import { WishlistsComponent } from './wishlists/wishlists.component';

const routes: Routes = [
  {path:'home', component:HomepageComponent},
  {path:'wishlists',component:WishlistsComponent},
  {path:'wishlists/:id',component:WishlistComponent},
  {path:'', redirectTo:'home',pathMatch:'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
