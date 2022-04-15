import { createFeatureSelector } from '@ngrx/store';
import { adapter } from './card.adapters';
import * as fromState from './card.state';

export const getCardState = createFeatureSelector<fromState.State>('cardState');

export const {
  selectIds: selectCardIds,
  selectEntities: selectCardEntities,
  selectAll: selectAllCards,
  selectTotal: selectTotalCards,
} = adapter.getSelectors();
