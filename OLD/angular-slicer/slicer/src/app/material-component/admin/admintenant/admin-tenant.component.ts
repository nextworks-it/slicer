import { DialogTenantSlaMecComponent } from './../../dialog-tenant-sla-mec/dialog-tenant-sla-mec.component';
import { DialogTenantSlaCloudComponent } from './../../dialog-tenant-sla-cloud/dialog-tenant-sla-cloud.component';
import { DialogTenantSlaGlobalComponent } from './../../dialog-tenant-sla-global/dialog-tenant-sla-global.component';
import { Component,OnInit ,ViewChild,Inject} from '@angular/core';
import { GroupService } from './../../../services/group.service';
import { TenantService } from './../../../services/tenant.service';
import{AdminTenantInfo} from '../admintenant/admin-tenant-info';
import{GroupInfo} from '../group/group-info';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import { MatDialog } from '@angular/material';
import { DialogTenantComponent } from '../dialog-tenant/dialog-tenant.component';
import{AdminTenantDataSource} from '../admintenant/admin-tenant.datasource';
import { Router } from '@angular/router';
import { ThrowStmt } from '@angular/compiler';
import { empty } from 'rxjs';
import { type } from 'jquery';
@Component({
  selector: 'app-group',
  templateUrl: './admin-tenant.component.html',
  styleUrls: ['./admin-tenant.component.scss']
})
export class AdminTenantComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<AdminTenantInfo>;

  name: string;
  dataSource: AdminTenantDataSource;
  adminTenantInfos: AdminTenantInfo[] = [];
  displayedColumns = ['Username','UsedResources','action'];
  showTest: boolean;
  showReal: boolean;
  admin : string
  selectedTenant: AdminTenantInfo[];
  selectedGroup: GroupInfo[];
  filterElement: string;
  sGroup : string;
  selected: 'admin';
  dialUsername : string;
  haveData : boolean;
  groupLogin:string;
  
  constructor(private groupService:GroupService,
              public dialog: MatDialog,
              private tenantService:TenantService,
              private router: Router){
    
  }

  ngOnInit() {
    this.groupLogin=localStorage.getItem('group')

    this.dataSource = new AdminTenantDataSource(this.adminTenantInfos);
      
      this.sGroup="admin";
      this.filterElement=this.sGroup;
      this.getGroups();
      this.getTenants(this.filterElement); 
      this.haveData=true;

  }

  getTenants(filterElement) {

      this.tenantService.getTenantData(filterElement).subscribe((tenantInfos : AdminTenantInfo[] )=> {
      this.selectedTenant = tenantInfos
      this.adminTenantInfos = tenantInfos['tenants'];
     // if(tenantInfos['tenants']!=undefined)
      //{
        this.haveData=true;
        this.dataSource = new AdminTenantDataSource(this.adminTenantInfos);
        this.dataSource.sort = this.sort;
        this.dataSource.paginator = this.paginator;
        this.table.dataSource = this.dataSource;
        //console.log("this.haveData",this.haveData,"this.table.dataSource",this.table.dataSource)
    //  }
      /*
      else{
        console.log("here we dont have tenants")
        this.haveData=false;
        this.table.dataSource=[]
        //console.log("this.haveData",this.haveData,"this.table.dataSource",this.table.dataSource)
      }
*/
     
    });
     
  }


  getGroups() {
    this.groupService.getGroupData().subscribe((groupInfos : GroupInfo[] )=> {
    this.selectedGroup = groupInfos
      
  });
}
  deleteTenant(sGroup,userId){
    console.log("sGroup,userId",sGroup,userId)
    this.tenantService.deleteTenant(sGroup,userId).subscribe();
    alert(userId+" is deleted");
    window.location.reload();

  }


 showDialogAddTenant(){
  const dialogRef = this.dialog.open(DialogTenantComponent, {
    width: '400px',
    height: '450px'
  }); 
}
showSlaMecDialog(username,sGroup){
  console.log("sGroup",sGroup)
  const dialogRef = this.dialog.open(DialogTenantSlaMecComponent, {
    width: '400px',
    height: '700px',
    data: {
      dialUsername: username,
      dialGroup:sGroup
    } 
  }); 
}

showSlaGlobalDialog(username,sGroup){
  const dialogRef = this.dialog.open(DialogTenantSlaGlobalComponent, {
    width: '400px',
    height: '700px',
    data: {
      dialUsername: username,
      dialGroup:sGroup
    }  
  }); 
}

showSlaCloudDialog(username,sGroup){
  const dialogRef = this.dialog.open(DialogTenantSlaCloudComponent, {
    width: '400px',
    height: '700px',
    data: {
      dialUsername: username,
      dialGroup:sGroup
    }
  }); 
}


onGroupSelection(){
  this.filterElement=this.sGroup;
  this.getTenants(this.filterElement)
}
showSla(userId){
  this.router.navigate(['/sla', userId]);
}
notShow(){
  console.log(document.getElementById("divShow").innerHTML);  
}
viewSla(sGroup,username){
  localStorage.setItem('slaUsername', username);
  localStorage.setItem('slaGroup', sGroup);
  this.router.navigate(["/sla"]);
}
}

