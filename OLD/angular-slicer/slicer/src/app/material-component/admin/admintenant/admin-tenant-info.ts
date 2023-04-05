export class AdminTenantInfo {

        username: string;
        password: string;
        allocatedResources: {
          diskStorage: number
          memoryRAM: number
          vCPU: number
        }

}