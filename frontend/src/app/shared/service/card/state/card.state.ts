import { EntityState } from "@ngrx/entity";
import { Card } from "src/app/shared/model/card.model";
import { adapter } from "./card.adapters";

export interface State extends EntityState<Card> {
  // no additional properties
}

export const initialState: State = adapter.getInitialState();
