import { createFeatureSelector, createSelector } from '@ngrx/store';
import { adapter } from './card.adapters';
import * as fromState from './card.state';

export const getCardState = createFeatureSelector<fromState.State>('cardState');

export const {
  selectIds: selectCardIds,
  selectEntities: selectCardEntities,
  selectAll: selectAllCards,
  selectTotal: selectTotalCards,
} = adapter.getSelectors(getCardState);

export const selectCardById = (cardId: number) =>
  createSelector(selectAllCards, cards => cards.find(c => c.id == cardId));

export const selectCardByName = (name: string) =>
  createSelector(selectAllCards, cards => cards.filter(c => c.name == name));

  export const selectCardByIdMap = (cardId: number) =>
  createSelector(selectCardEntities, cards=> cards[cardId]);
