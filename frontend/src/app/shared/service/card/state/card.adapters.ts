import { createEntityAdapter, EntityAdapter } from "@ngrx/entity";
import { Card } from "src/app/shared/model/card.model";

export const adapter: EntityAdapter<Card> = createEntityAdapter<Card>();
