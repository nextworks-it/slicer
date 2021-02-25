import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { GroupService } from '../../../services/group.service';
import { Router } from '@angular/router';
@Component({
  selector: 'app-dialog',
  templateUrl: './dialog-group.component.html',
  styleUrls: ['./dialog-group.component.scss']
})
export class DialogGroupComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DialogGroupComponent>,
    private groupService:GroupService,
    private router: Router
  ) { }

  ngOnInit() {
    this.dialogRef.updatePosition({ top: `15%`,
    right: `40%`});
  }
  addGroup(groupId){
    var gRequest =(<HTMLInputElement>document.getElementById(groupId)).value;
    this.groupService.postGroup(gRequest).subscribe
    (groupId => window.location.reload()); 
  }




}