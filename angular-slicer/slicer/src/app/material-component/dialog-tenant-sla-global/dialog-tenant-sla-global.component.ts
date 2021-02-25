import { Component, OnInit ,Inject} from '@angular/core';
import { MatDialogRef,MAT_DIALOG_DATA } from '@angular/material';
import { GroupService } from '../../services/group.service';
import { TenantService } from '../../services/tenant.service';
import { Router } from '@angular/router';
import { report } from 'process';

@Component({
  selector: 'app-dialog-tenant-sla-global',
  templateUrl: './dialog-tenant-sla-global.component.html',
  styleUrls: ['./dialog-tenant-sla-global.component.css']
})
export class DialogTenantSlaGlobalComponent implements OnInit {


  constructor(public dialogRef: MatDialogRef<DialogTenantSlaGlobalComponent>,
    private groupService:GroupService,
    private router: Router,
    private tenantService:TenantService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }
  selectedAdmin : string;
  selected: 'admin';
  tenant =this.data['dialUsername']
  ngOnInit() {
    this.dialogRef.updatePosition({ top: `5%`,
    right: `40%`});
  }
  addGlobalSla(slaStatus,scope,location,memoryRAM,vCPU,diskStorage){
    var globalSlatRequest = JSON.parse('{}');
    var status =(<HTMLInputElement>document.getElementById(slaStatus)).value;
    //var scp =(<HTMLInputElement>document.getElementById(scope)).value;
    var loc =(<HTMLInputElement>document.getElementById(location)).value;
    var dStorage =(<HTMLInputElement>document.getElementById(diskStorage)).value;
    var mRAM =(<HTMLInputElement>document.getElementById(memoryRAM)).value;
    var vcPU =(<HTMLInputElement>document.getElementById(vCPU)).value;
    
    globalSlatRequest['tenant']=this.data['dialUsername'];
    globalSlatRequest['slaStatus']=status;
    globalSlatRequest['slaConstraints']=[];
    globalSlatRequest['slaConstraints'].push({'scope':'GLOBAL_VIRTUAL_RESOURCE','location':loc,'maxResourceLimit':{'memoryRAM':mRAM,'vCPU':vcPU,'diskStorage':dStorage}})
    this.tenantService.postSlaData(globalSlatRequest,this.data['dialGroup'],this.data['dialUsername']).subscribe(respId => console.log("global reponse id : " + respId))
  }


}
