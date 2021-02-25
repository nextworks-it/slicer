

import { Component, OnInit,ViewChild } from '@angular/core';
import { TenantService } from '../../../services/tenant.service';
import { SlaInfo } from './sla-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{ SlaDataSource } from './sla-datasource';
import { ActivatedRoute } from "@angular/router";

@Component({
  selector: 'app-sla',
  templateUrl: './sla.component.html',
  styleUrls: ['./sla.component.css']
})
export class SlaComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<SlaInfo>;
  name: string;
  dataSource: SlaDataSource;
  slaInfos: SlaInfo[] = [];
  displayedColumns = ['Id','Scope','MaxRAM','MaxVCPUs','MaxVStorage','Location','Status','action'];
  constructor(private tenantService:TenantService,private route: ActivatedRoute) { }
  groupId : string;
  userId : string;

  ngOnInit(): void {
    this.groupId = localStorage.getItem('slaGroup');
    this.userId = localStorage.getItem('slaUsername');
    /*
   this.groupId = this.route.snapshot.paramMap.get("groupId");
   this.userId = this.route.snapshot.paramMap.get("userId");
   console.log("this.groupId",this.groupId," this.userId", this.userId)
   */
   this.getTenantSLAs(this.groupId,this.userId);
  }

  
  getTenantSLAs(groupId,userId){
    this.tenantService.getTenantSLAsData(groupId,userId).subscribe((slaInfos : SlaInfo[])=> {
    this.slaInfos = slaInfos;
    this.dataSource = new SlaDataSource(this.slaInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
  });
}

deleteSla(slaId){
  this.tenantService.deleteSLAs(this.groupId,this.userId,slaId).subscribe();
  alert(slaId+" is deleted");
  window.location.reload();
}
viewSla(userId){
  console.log(userId)
}
}
