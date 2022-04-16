import { Action, createReducer, on } from "@ngrx/store";
import * as fromState from './card.state';
import * as fromActions from './card.actions';
import { adapter } from "./card.adapters";

export const cardReducer = createReducer(
  fromState.initialState,
  on(fromActions.loadCardsSuccess, (state, { apiData }) => {
    return adapter.setAll(apiData.data, state);
  }),
);

export function reducer(state: fromState.State | undefined, action: Action ): fromState.State {
  return cardReducer(state, action);
}
