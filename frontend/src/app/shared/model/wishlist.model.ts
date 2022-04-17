import { CardAmount } from "./cardamount.model";
import { User } from "./user.model";

export interface Wishlist{
    id:number;
    cards:CardAmount[];
    owner:User;
    name:string;
    sharedUsers:User[];
    totalCards:number;
}