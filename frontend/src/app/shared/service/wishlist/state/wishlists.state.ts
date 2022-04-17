import { Wishlist } from 'src/app/shared/model/wishlist.model';

export interface State {
    wishlists: Wishlist[]
}

export const initialState: State = {
    wishlists:[]
}


