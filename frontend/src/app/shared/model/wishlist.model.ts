import { CardAmount } from "./cardamount.model";
import { User } from "./user.model";

export interface Wishlist{
    id:number;
    cards:CardAmount[];
    owner:string;
    name:string;
    shareUsers:User[];
}