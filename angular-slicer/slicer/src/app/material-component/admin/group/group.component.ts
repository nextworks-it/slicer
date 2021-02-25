
import { Component,OnInit ,ViewChild,Inject} from '@angular/core';
import { GroupService } from './../../../services/group.service';
import{GroupInfo} from './group-info';
import{GroupDataSource} from './group-datasource';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import * as glob from '../../../global';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { DialogGroupComponent } from '../dialog-group/dialog-group.component';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.scss']
})
export class GroupComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<GroupInfo>;
  name: string;
  dataSource: GroupDataSource;
  groupInfos: GroupInfo[] = [];
  displayedColumns = ['name','tenants','action'];
  showTest: boolean;
  showReal: boolean;
   groupFakeData = [
    { tenants: {username:"fake username 1",password:"fake password 1",allocatedResources: {diskStorage:0,memoryRAM:0,vCPU:0}} ,name: 'fakeadmin 1'},
    { tenants: {username:"fake username 2",password:"fake password 2",allocatedResources: {diskStorage:0,memoryRAM:0,vCPU:0}} ,name: 'fakeadmin 2'},
    { tenants: {username:"fake username 3",password:"fake password 3",allocatedResources: {diskStorage:0,memoryRAM:0,vCPU:0}} ,name: 'fakeadmin 3'}
  ];
  constructor(private groupService:GroupService,public dialog: MatDialog){}

  ngOnInit() {
      this.dataSource = new GroupDataSource(this.groupInfos);
      this.getGroup(); 
  }

  getGroup() {
      this.groupService.getGroupData().subscribe((groupInfos : GroupInfo[] )=> {
      if (glob.fakeTesetMode =='True'){
        this.groupInfos = this.groupFakeData;
        this.showTest= true;
      }
      else{
        this.groupInfos = groupInfos;
        this.showReal=true;
      }
      this.dataSource = new GroupDataSource(this.groupInfos);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
      this.table.dataSource = this.dataSource;
   
    });
  }
  deleteGroup(groupId: string){
    this.groupService.deleteGroup(groupId).subscribe();
    //alert(groupId+" is deleted");
    //window.location.reload();
  }


 showDialog(){
  const dialogRef = this.dialog.open(DialogGroupComponent, {
    width: '450px',
    height: '200px'
  }); 
  setTimeout(() => {
    dialogRef.close();
  }, 10000);
}
panelOpenState = false;
  step = 0;

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }
}

