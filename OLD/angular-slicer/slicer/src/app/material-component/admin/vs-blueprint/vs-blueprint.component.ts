import { Component, OnInit,ViewChild } from '@angular/core';
import { VsBlueprintsService } from '../../../services/vs-Blueprints.service';
import {VsBlueprintInfo} from './vs-blueprint-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{VsBlueprintDataSource} from './vs-blueprint-datasource';
import { VsBlueprintDetailsService } from '../../../services/vs-blueprint-details.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-blueprint',
  templateUrl: './vs-blueprint.component.html',
  styleUrls: ['./vs-blueprint.component.css']
})
export class VsBlueprintComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<VsBlueprintInfo>;
  name: string;
  dataSource: VsBlueprintDataSource;
  vsBlueprintInfos: VsBlueprintInfo[] = [];
  groupLogin:string;
  displayedColumns = ['name','version','description','vsds','details','action'];
  constructor(private vsBlueprintsService:VsBlueprintsService,
    private vsbDetailsService: VsBlueprintDetailsService,private router: Router) { }

  ngOnInit(): void {
    this.getVsBlueprint();
    this.groupLogin=localStorage.getItem('group')

  }
  getVsBlueprint() {
    this.vsBlueprintsService.getVsBlueprintsData().subscribe((vsBlueprintInfos : VsBlueprintInfo[] )=> {
    this.vsBlueprintInfos = vsBlueprintInfos;
    this.dataSource = new VsBlueprintDataSource(this.vsBlueprintInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
 
  });
}
deleteVsBlueprint(vsBlueprintId,vsBlueprintName){
  this.vsBlueprintsService.deleteVsBlueprintsData(vsBlueprintId).subscribe();
  alert(vsBlueprintName+" is deleted");
  window.location.reload();
}
viewVsBlueprintGraph(vsBlueprintId: string) {
  //console.log(vsBlueprintId);
  this.vsbDetailsService.updateVSBId(vsBlueprintId);

  //routerLink="/blueprints_vs_graph"
  this.router.navigate(["/blueprints-vs-details"]);
}
}



