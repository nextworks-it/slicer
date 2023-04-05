import { Component, OnInit,ViewChild } from '@angular/core';
import { VsDescriptorsService } from '../../../services/vs-descriptors.service';
import {VsDescriptorInfo} from './vs-descriptor-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{VsDescriptorDataSource} from './vs-descriptor-datasource';

@Component({
  selector: 'app-vs-descriptor',
  templateUrl: './vs-descriptor.component.html',
  styleUrls: ['./vs-descriptor.component.css']
})
export class VsDescriptorComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsDescriptorInfo>;
  name: string;
  dataSource: VsDescriptorDataSource;
  vsDescriptorInfos: VsDescriptorInfo[] = [];
  displayedColumns = ['id','name','version','vsb','action'];
  constructor(private vsDescriptorsService:VsDescriptorsService) { }
  groupLogin:string
  ngOnInit(): void {
    this.getVsDescriptor();
    this.groupLogin=localStorage.getItem('group')
  }
  getVsDescriptor() {
    this.vsDescriptorsService.getVsDescriptorsData().subscribe((vsDescriptorInfos : VsDescriptorInfo[] )=> {
    this.vsDescriptorInfos = vsDescriptorInfos;
    this.dataSource = new VsDescriptorDataSource(this.vsDescriptorInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
 
  });
}

  deleteVsDesc(vsDescId: string,vsDname: string,){
  this.vsDescriptorsService.deleteVsDescriptorsData(vsDescId).subscribe();
  alert(vsDname+" is deleted");
  window.location.reload();
}
}