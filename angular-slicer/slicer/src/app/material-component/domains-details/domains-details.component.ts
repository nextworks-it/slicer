import { DomainsService } from './../../services/domains.service';
import { Component, OnInit,ViewChild } from '@angular/core';
import {DomainsInfo} from '../domains/domains-info'
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTable } from '@angular/material/table';
import{DomainsDataSource} from '../domains/domains-datasource';
import { ActivatedRoute } from "@angular/router";
@Component({
  selector: 'app-domains-details',
  templateUrl: './domains-details.component.html',
  styleUrls: ['./domains-details.component.css']
})
export class DomainsDetailsComponent implements OnInit {
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatTable, {static: false}) table: MatTable<DomainsInfo>;
  name: string;
  dataSource: DomainsDataSource;
  domainsInfos: DomainsInfo[] = [];
  displayedColumns = ['url','port','ownedlayers'];
  constructor(private domainsService:DomainsService,private route: ActivatedRoute) { }
  domainId : string;
  ngOnInit(): void {
    this.domainId = localStorage.getItem('domainId');
    this.getDomainsDetails(this.domainId);
    
  }
  getDomainsDetails(domainId) {
    var domainInfsArr=[]
    this.domainsService.getDomainsDetailsData(domainId).subscribe((domainsInfos : DomainsInfo[] )=> {
    domainInfsArr.push(domainsInfos);
    this.domainsInfos = domainInfsArr;
    this.dataSource = new DomainsDataSource(this.domainsInfos);
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
    this.table.dataSource = this.dataSource;
    });
}
}
