import { DomainsService } from './../../services/domains.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import {DomainsInfo} from './domains-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{DomainsDataSource} from './domains-datasource';
import { Router } from '@angular/router';
@Component({
  selector: 'app-domains',
  templateUrl: './domains.component.html',
  styleUrls: ['./domains.component.css']
})
export class DomainsComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<DomainsInfo>;
  name: string;
  dataSource: DomainsDataSource;
  domainsInfos: DomainsInfo[] = [];
  displayedColumns = ['name','description','owner','admin','domainStatus','details','action'];
  constructor(private domainsService:DomainsService,private router: Router) { }

  ngOnInit(): void {
    this.getDomains();
  }
  getDomains() {
    this.domainsService.getDomainsData().subscribe((domainsInfos : DomainsInfo[] )=> {
    this.domainsInfos = domainsInfos;
    this.dataSource = new DomainsDataSource(this.domainsInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
 
  });
}
viewDomainDetails(domainId){
  localStorage.setItem('domainId', domainId);
  this.router.navigate(["/domains-details"]);

}


deleteDomain(domainId){
  console.log("domainId",domainId)
  this.domainsService.deleteDomain(domainId).subscribe();
  alert(domainId+" is deleted");
  window.location.reload();

}
}
