import { CardImage } from "./card-image.model";
import { CardPrice } from "./card-price.model";
import { CardSet } from "./card-set.model";

export interface Card {
  id: number;
  name: string;
  type: string;
  desc: string;
  race: string;
  archetype?: string;
  atk?: number;
  def?: number;
  level?: number;
  attribute?: string;
  card_sets: CardSet[];
  card_images: CardImage[];
  card_prices: CardPrice[];
}
