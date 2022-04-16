import { createAction, props } from "@ngrx/store";
import { User } from "../../../model/user.model";

export const getWishlistsByUserId = createAction('[Wishlist] GetWishlistsByUserId', props<{userId:number}>());