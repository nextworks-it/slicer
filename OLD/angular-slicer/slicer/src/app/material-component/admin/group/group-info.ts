export class GroupInfo {
    tenants: {
        username: string;
        password: string;
        allocatedResources: {
          diskStorage: number
          memoryRAM: number
          vCPU: number
        }
      };
    name: string;

}