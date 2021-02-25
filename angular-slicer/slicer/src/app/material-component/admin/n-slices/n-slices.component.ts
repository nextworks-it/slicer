import { Component, OnInit,ViewChild } from '@angular/core';
import { NslicesService } from '../../../services/n-slices.service';
import { NslicesInfo } from './n-slices-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{NslicesDataSource} from './n-slices-datasource';

@Component({
  selector: 'app-n-slices',
  templateUrl: './n-slices.component.html',
  styleUrls: ['./n-slices.component.css']
})
export class NSlicesComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<NslicesInfo>;
  name: string;
  dataSource: NslicesDataSource;
  nslicesInfos: NslicesInfo[] = [];
  displayedColumns = ['name','version','provider','nssts','action'];
  constructor(private nslicesService:NslicesService) {  }

  ngOnInit(): void {
   
    this.getNslices();
  }
  getNslices() {
    this.nslicesService.getNslicesData().subscribe((nslicesInfos : NslicesInfo[] )=> {
    this.nslicesInfos = nslicesInfos;
    //console.log("vsDescriptorInfos",vsDescriptorInfos)
    this.dataSource = new NslicesDataSource(this.nslicesInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
 
  });
}

deleteNslices(nstId,nstName){
  this.nslicesService.deleteNslicesData(nstId).subscribe();
  alert(nstId +" is deleted");
  window.location.reload();

}
}