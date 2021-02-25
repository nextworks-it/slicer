export class SlaInfo {
    id: string;
    slaStatus : string;
    slaConstraints:[
        {
            location : string;
            scope : string;
            maxResourceLimit:{
                diskStorage:number;
                memoryRAM : number;
                vCPU : number;


            }
        }
    ]



}