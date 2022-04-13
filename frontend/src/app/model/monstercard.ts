import { SpellTrapCard } from "./spelltrapcard";

export class MonsterCard extends SpellTrapCard{
    private _atk:number;
    private _def:number;
    private _level:number;
    private _attribute:string;

    constructor(id:number,name:string,type:string,desc:string,race:string,atk:number, def:number, level:number, attribute:string){
        super(id,name,type,desc,race);
        this._atk = atk;
        this._def = def;
        this._level = level;
        this._attribute = attribute;
    }

    
    get atk():number {
        return this._atk;
    }

    set atk(atk:number){
        this._atk = atk;
    }

    get def():number {
        return this._def;
    }

    set def(def:number){
        this._def = def;
    }

    get level():number {
        return this._level;
    }

    set level(level:number){
        this._level = level;
    }

    get attribute():string {
        return this._attribute;
    }

    set attribute(attribute:string){
        this.attribute = attribute;
    }

}