import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { GroupService } from '../../../services/group.service';
import { TenantService } from '../../../services/tenant.service';
import { Router } from '@angular/router';
import {GroupInfo} from '../group/group-info';

@Component({
  selector: 'app-dialog',
  templateUrl: './dialog-tenant.component.html',
  styleUrls: ['./dialog-tenant.component.scss']
})
export class DialogTenantComponent implements OnInit {
  selectedGroup: GroupInfo[];
  groupInfos: GroupInfo[] = [];

  constructor(public dialogRef: MatDialogRef<DialogTenantComponent>,
    private groupService:GroupService,
    private router: Router,
    private tenantService:TenantService
  ) { }
  selectedAdmin : string;
  selected: 'admin';
  ngOnInit() {
    this.dialogRef.updatePosition({ top: `5%`,
    right: `40%`});
    this.getGroup(); 
  }
  addTenant(username,password,groupId){
    var tenantRequest = JSON.parse('{}');
    var userName =(<HTMLInputElement>document.getElementById(username)).value;
    var psw =(<HTMLInputElement>document.getElementById(password)).value;
    tenantRequest['username']=userName;
    tenantRequest['password']=psw;
    console.log("this.selectedAdmin",this.selectedAdmin)
    this.tenantService.postTenantData(tenantRequest,this.selectedAdmin).subscribe(tenantId =>window.location.reload())
    
  }

  getGroup() {
    this.groupService.getGroupData().subscribe((groupInfos : GroupInfo[] )=> {
    this.selectedGroup = groupInfos
  });
}


}