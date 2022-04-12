import { createFeatureSelector } from '@ngrx/store'
import * as fromState from './auth.state'

export const selectAuthState = createFeatureSelector<fromState.State>('authState');
