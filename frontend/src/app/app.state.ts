import { ActionReducerMap } from "@ngrx/store";
import { AuthReducers, AuthState } from "./shared/auth/service/state";

export interface AppState {
  authState: AuthState.State
}

export const reducers: ActionReducerMap<AppState> = {
  authState: AuthReducers.reducer,
}
