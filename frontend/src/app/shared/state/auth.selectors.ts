import { createFeatureSelector, createSelector } from '@ngrx/store'
import * as fromState from './auth.state'

export const selectAuthState = createFeatureSelector<fromState.State>('authState');

export const selectErrorMessage = createSelector(
  selectAuthState,
  state => state.errorMessage
);

export const selectIsAuthenticated = createSelector(
  selectAuthState,
  state => state.isAuthenticated
);

export const selectAuthUser = createSelector(
  selectAuthState,
  state => state.user
);
