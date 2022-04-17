import { createAction, props } from "@ngrx/store";
import { Wishlist } from "src/app/shared/model/wishlist.model";
import { User } from "../../../model/user.model";

export const getWishlistsByUserId = createAction('[Wishlist] GetWishlistsByUserId', props<{userId: number}>());
export const retrievedWishlistsByUserId = createAction('[Wishlist] RetirevedWishlistsByUserId',props<{wishlists: Wishlist[]}>());
export const getWishlistById = createAction('[Wishlist] GetWishlistById',props<{wishlistId: number}>());