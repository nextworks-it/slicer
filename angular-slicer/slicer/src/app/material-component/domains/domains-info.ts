
export class DomainsInfo {
    domainId :string;
    name: string;
    description:number;
    owner:string;
    admin:string;
    domainStatus:string;
    domainInterface : {
        url:string,
        port: number,
        auth: boolean,
        interfaceType: string
    };
    ownedLayers: [
        {
            type:string,
            domainLayerId:string,
            domainLayerType:string,
            manoNbiType:string,
            username:string,
            password:string,
            project:string
        }
    ]
}