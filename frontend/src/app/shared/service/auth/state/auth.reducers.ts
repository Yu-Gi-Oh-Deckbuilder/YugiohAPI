import { Action, createReducer, on } from '@ngrx/store'
import * as fromActions from './auth.actions'
import * as fromState from './auth.state'

const authReducer = createReducer(
  fromState.initialState,
  on(fromActions.loginSuccess, (state, {user, token}) => {
    return {
      ...state,
      isAuthenticated: true,
      token: token,
      user: user,
      errorMessage: null,
    }
  }),
  on(fromActions.loginFail, (state, { message }) => {
    return {
      ...state,
      errorMessage: message,
    }
  }),
  on(fromActions.logout, () => fromState.initialState),
);

export function reducer(state: fromState.State | undefined, action: Action): fromState.State {
  return authReducer(state, action);
}
