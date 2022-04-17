import { ActionReducerMap } from "@ngrx/store";
import { AuthReducers, AuthState } from "./shared/service/auth/state";
import { CardReducers, CardState } from "./shared/service/card/state";
import { WishlistsReducers, WishlistsState } from "./shared/service/wishlist/state";

export interface AppState {
  authState: AuthState.State,
  cardState: CardState.State,
  wishlistsState: WishlistsState.State,
}

export const reducers: ActionReducerMap<AppState> = {
  authState: AuthReducers.reducer,
  cardState: CardReducers.reducer,
  wishlistsState: WishlistsReducers.reducer,
}
