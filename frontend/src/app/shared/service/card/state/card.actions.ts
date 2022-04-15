import { createAction, props } from "@ngrx/store";
import { ApiData } from "src/app/shared/model/api-data.model";

export const loadCards = createAction('[Root] Load Cards from API');
export const loadCardsSuccess = createAction('[Root] Load Cards from API Success', props<{ apiData: ApiData }>());
export const loadCardsFail = createAction('[Root] Load Cards from API Fail', props<{ message: string }>());
