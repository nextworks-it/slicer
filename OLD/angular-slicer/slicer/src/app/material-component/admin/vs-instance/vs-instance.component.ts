import { Component, OnInit ,ViewChild } from '@angular/core';
import {VsInstancesService} from '../../../services/vs-instances.service';
import {VsInstanceInfo} from './vs-instance-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import {VsInstanceDataSource} from './vs-instance-datasource'
import {MatTableDataSource} from '@angular/material';
import { Router } from '@angular/router';
@Component({
  selector: 'app-vs-instance',
  templateUrl: './vs-instance.component.html',
  styleUrls: ['./vs-instance.component.css'],
  
})
export class VsInstanceComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsInstanceInfo>;

  constructor(private vsInstancesService:VsInstancesService,private router: Router) { }
  dataSource;
  vsIds=[]
  vsInstanceInfos: VsInstanceInfo[] = [];
  displayedColumns = ['id','name','description','details','action'];
  //dataSource = new MatTableDataSource<VsInstanceInfo>();

  groupLogin:string;
  ngOnInit(): void {
    this.getVsInstances();
    this.groupLogin=localStorage.getItem('group');

  }

  viewVsiDetails(vsiId){
    localStorage.setItem('vsiId', vsiId);
    this.router.navigate(["/vsi-details"]);
  
  }
  deleteVsi(vsiId){
    this.vsInstancesService.deleteVsInstance(vsiId).subscribe();
   // alert(vsiId+" is deleted");
    //window.location.reload();
  
  }
  terminateVsi(vsiId){
    this.vsInstancesService.terminateVsInstance(vsiId).subscribe();
    alert(vsiId+" is terminated");
    //window.location.reload();
  }
  getVsInstances() {
    let vsElements=[]
    this.vsInstancesService.getVsInstancesData().subscribe((vsInstancesInfos )=> {
      console.log("vsInstancesInfos",vsInstancesInfos)
      for(var i=0;i<Object.keys(vsInstancesInfos).length;i++){
 
          this.vsInstancesService.getVsInstanceByIdData(vsInstancesInfos[i]).subscribe((vsInstanceInffos : VsInstanceInfo[] )=> {
            vsElements.push(     
               { 
              vsiId: vsInstanceInffos['vsiId'],
              name:vsInstanceInffos['name'],
              description:vsInstanceInffos['description']
            })

          this.dataSource = new MatTableDataSource(vsElements);
          });

      }
  });
}
}
