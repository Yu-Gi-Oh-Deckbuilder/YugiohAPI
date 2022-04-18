import { createFeatureSelector, createSelector } from '@ngrx/store'
import * as wishlistsState from './wishlists.state'

export const selectWishlistState = createFeatureSelector<wishlistsState.State>('wishlistsState');

// export const selectErrorMessage = createSelector(
//   selectAuthState,
//   state => state.errorMessage
// );

// export const selectIsAuthenticated = createSelector(
//   selectAuthState,
//   state => state.isAuthenticated
// );

export const selectWishlists = createSelector(
  selectWishlistState,
  state => state.wishlists
);

export const selectWishlistById = (wishlistId:number) =>createSelector(
  selectWishlists,
  wishlists => wishlists.find(wishlist => wishlist.id == wishlistId))
