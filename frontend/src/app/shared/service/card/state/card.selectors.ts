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
  createSelector(selectAllCards, cards => cards.find(c => c.name == name));

  export const selectCardByIdMap = (cardId: number) =>
  createSelector(selectCardEntities, cards=> cards[cardId]);

  export const selectFilteredCards = (filter: string | number) =>
  createSelector(selectAllCards, cards =>
    cards.filter(c => c.archetype?.includes(filter as string) || c.type.includes(filter as string)
      || c.atk == (filter as number) || c.attribute?.includes(filter as string)
      || c.def == (filter as number) || c.desc.includes(filter as string)
      || c.id == (filter as number) || c.level == (filter as number)
      || c.name.includes(filter as string) || c.race.includes(filter as string))
  )

export const selectPaginatedCards = (start: number, end: number) =>
  createSelector(selectAllCards, cards => cards.slice(start, end));

export const selectFilteredAndPaginatedCards = (filter: string | number, start: number, end: number) => createSelector(
  selectFilteredCards(filter),
  cards => cards.slice(start, end)
);

export const selectFilteredTotal = (filter: string | number) => createSelector(
  selectFilteredCards(filter),
  cards => cards.length
);

export const selectAllCardNames = createSelector(
  selectAllCards,
  cards => cards.map(card=>card.name)
);

export const selectIdsFromCardNames = (names: string[]) => createSelector(
  selectAllCards,
  cards => {
    const cardNames = cards.filter(card => names.includes(card.name));
    return cardNames.map(card => card.id);
  }
);
