export class LoginInfo {
    username: number;
    password: string;
    allocatedResources: {
        diskStorage: number
        memoryRAM: number
        vCPU: number
      }
    role :string
};