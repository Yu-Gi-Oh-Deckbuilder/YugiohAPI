import { ActionReducerMap } from "@ngrx/store";
import { AuthReducers, AuthState } from "./shared/service/auth/state";
import { CardReducers, CardState } from "./shared/service/card/state";

export interface AppState {
  authState: AuthState.State,
  cardState: CardState.State,
}

export const reducers: ActionReducerMap<AppState> = {
  authState: AuthReducers.reducer,
  cardState: CardReducers.reducer,
}
