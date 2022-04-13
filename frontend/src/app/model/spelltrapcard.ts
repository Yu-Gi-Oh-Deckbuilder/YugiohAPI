export class SpellTrapCard {
    private _id: number;
    private _name:string;
    private _type:string;
    private _desc:string;
    private _race:string

    constructor(id:number,name:string,type:string,desc:string,race:string){
        this._id = id;
        this._name = name;
        this._type = type;
        this._desc = desc;
        this._race = race;
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

    get race():string {
        return this._race;
    }

    set race(race:string){
        this._race = race;
    }
}