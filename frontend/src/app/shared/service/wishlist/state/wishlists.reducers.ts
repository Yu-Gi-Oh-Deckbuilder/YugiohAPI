import { Action, createReducer, on } from "@ngrx/store";
import * as wishlistsState from './wishlists.state';
import * as wishlistsActions from './wishlists.actions';

const wishlitsReducer = createReducer(
    wishlistsState.initialState,
    on(
        wishlistsActions.retrievedWishlistsByUserId,(state, { wishlists })=>{
            return {
                ...state,
                wishlists: wishlists
            }
        }
    ),
)

export function reducer(state:wishlistsState.State | undefined,action: Action):wishlistsState.State{
    return wishlitsReducer(state,action)
}