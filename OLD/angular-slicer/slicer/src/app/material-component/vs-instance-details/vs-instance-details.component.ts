import { VsInstanceInfo } from './../admin/vs-instance/vs-instance-info';

import { VsInstancesService } from './../../services/vs-instances.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{VsInstanceDataSource} from './../admin/vs-instance/vs-instance-datasource';
import { ActivatedRoute } from "@angular/router";


@Component({
  selector: 'app-vs-instance-details',
  templateUrl: './vs-instance-details.component.html',
  styleUrls: ['./vs-instance-details.component.css']
})
export class VsInstanceDetailsComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsInstanceInfo>;
  name: string;
  dataSource: VsInstanceDataSource;
  vsInstanceInfos: VsInstanceInfo[] = [];
  displayedColumns = ['vsdId','status','errorMessage'];


  constructor(private vsInstancesService:VsInstancesService,private route: ActivatedRoute) { }
  vsiId : string;

  ngOnInit(): void {
    this.vsiId = localStorage.getItem('vsiId');
    this.getVsiDetails(this.vsiId);
    
  }

  getVsiDetails(vsiId) {
    var vsiInfsArr=[]
    this.vsInstancesService.getVsInstanceByIdData(vsiId).subscribe((vsInstanceInfos : VsInstanceInfo[] )=> {
    vsiInfsArr.push(vsInstanceInfos);
    this.vsInstanceInfos = vsiInfsArr;
    this.dataSource = new VsInstanceDataSource(this.vsInstanceInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    });
    
}
}
