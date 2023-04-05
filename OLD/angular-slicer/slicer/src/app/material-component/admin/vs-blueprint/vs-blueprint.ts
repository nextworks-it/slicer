export class VsBlueprint {
    blueprintId: string;
    version: string;
    name: string;
    description: string;
    parameters: Object[];
    atomicComponents: Object[];
    serviceSequence: Object[];
    endPoints: Object[];
    connectivityServices: Object[];
    applicationMetrics: Object[];
    compatibleSites: string[];
    compatibleContextBlueprint: string[];
}