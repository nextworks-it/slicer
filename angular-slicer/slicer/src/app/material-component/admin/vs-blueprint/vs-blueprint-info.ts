/*
export class VsBlueprintInfo {
    name: string;
    vsBlueprintId:number;
    vsBlueprint :{
        description : string;
    }


}
*/
import { VsBlueprint } from './vs-blueprint';

export class VsBlueprintInfo {
    vsBlueprintId: string;
    vsBlueprintVersion: string;
    name: string;
    onBoardedNsdInfoId: string[];
    onBoardedVnfPackageInfoId: string[];
    onBoardedMecAppPackageInfoId: string[];
    activeVsdId: string[];
    vsBlueprint: VsBlueprint;
}