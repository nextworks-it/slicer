import { Component, OnInit,ViewChild } from '@angular/core';
import { NsliceInstancesService } from '../../../services/n-slice-instances.service';
import { NsliceInstancesInfo } from './n-slice-instances-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{NsliceInstancesDataSource} from './n-slice-instances-datasource';

@Component({
  selector: 'app-n-slice-instances',
  templateUrl: './n-slice-instances.component.html',
  styleUrls: ['./n-slice-instances.component.css']
})
export class NSliceInstancesComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<NsliceInstancesInfo>;
  name: string;
  dataSource: NsliceInstancesDataSource;
  nslicesInfos: NsliceInstancesInfo[] = [];
  displayedColumns = ['id','name','description','status','nssis','action'];
  constructor(private nsliceInstancesService:NsliceInstancesService) {  }

  ngOnInit(): void {
   
    this.getNslices();
  }
  
  getNslices() {
    this.nsliceInstancesService.getNsliceInstancesData().subscribe((nsliceInstancesInfos : NsliceInstancesInfo[] )=> {
    this.nslicesInfos = nsliceInstancesInfos;
    this.dataSource = new NsliceInstancesDataSource(this.nslicesInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
 
  });
}

deleteNsliceInstances(nsiId,nsiName){
  this.nsliceInstancesService.deleteNsliceInstancesData(nsiId).subscribe();
  //alert(nsiName +" is deleted");
 // window.location.reload();

}
}