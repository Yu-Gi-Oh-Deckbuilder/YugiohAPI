export class Card{
    _id: number;
    _name:string;
    _type:string;
    _desc:string;
    

    constructor(id:number,name:string,type:string,desc:string){
        this._id = id;
        this._name = name;
        this._type = type;
        this._desc = desc;
    }

    get id():number{
        return this._id;
    }

    set id(id:number){
        this._id = id;
    }

    get name():string {
        return this._name;
    }

    set name(name:string){
        this._name = name;
    }

    
    get type():string {
        return this._type;
    }

    set type(type:string){
        this._type = type;
    }

    
    get desc():string {
        return this._desc;
    }

    set desc(desc:string){
        this._desc = desc;
    }

}