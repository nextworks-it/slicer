
/*
import { VsBlueprintsService } from './../services/vs-Blueprints.service';
import { GroupService } from './../services/group.service';
import { Component, OnInit } from '@angular/core';
import { VsDescriptorsService } from './../services/vs-descriptors.service';
import { VsInstancesService } from './../services/vs-instances.service';
import { NslicesService } from './../services/n-slices.service';
import { DomainsService } from './../services/domains.service';
import { environment } from './../environments/environments';


@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

	groupCounter: number = 0;
	tenantsCounter: number = 0;
	vsDescriptorsCounter: number = 0;
	vsInstanceCounter: number = 0;
	vsBlueprintsCounter: number = 0;
	nsiCounter : number = 0;
	domainCounter : number = 0;
	breakpoint : any;
	group:string;
	items:any;
	counters: number[] = [
		this.tenantsCounter, 
		this.vsDescriptorsCounter, 
		this.vsInstanceCounter, 
		this.groupCounter,
		this.vsBlueprintsCounter,
		this.nsiCounter,
		this.domainCounter
	];
	constructor(
		private groupService: GroupService,
		private vsDescriptorsService: VsDescriptorsService,
		private vsInstancesService: VsInstancesService,
		private vsBlueprintsService: VsBlueprintsService,
		private nslicesService: NslicesService,
		private domainsService: DomainsService,
		

		){}
	ngOnInit(): void {
		this.group=localStorage.getItem("group");
		if(environment.deploymentType=='VSMF' && this.group=="ADMIN"){
			this.items = [
				{ title: 'Tenants', path: '/tenant', icon:'person',count :this.tenantsCounter},
				{ title: 'VS descriptors', path: '/vsdescriptor', icon:'note',count :this.vsDescriptorsCounter},
				{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
				{ title: 'Groups', path: '/group', icon:'group',count : this.groupCounter},
				{ title: 'VS Blueprints', path: '/vsblueprint', icon:'note',count :this.vsBlueprintsCounter},
				{ title: 'Network Slice instances', path: '/nslices', icon:'note',count :this.nsiCounter},
				{ title: 'Domain', path: '/domains', icon:'note',count :this.domainCounter}
		
			  ];		  
		}
		if(environment.deploymentType=='VSMF' && this.group=="TENANT"){
			this.items = [
				{ title: 'VS descriptors', path: '/vsdescriptor', icon:'note',count :this.vsDescriptorsCounter},
				{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
				{ title: 'VS Blueprints', path: '/vsblueprint', icon:'note',count :this.vsBlueprintsCounter},
			  ];		  
		}
		if(environment.deploymentType=='NSMF' && this.group=="ADMIN"){
			this.items = [
				{ title: 'Tenants', path: '/tenant', icon:'person',count :this.tenantsCounter},
				{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
				{ title: 'Groups', path: '/group', icon:'group',count : this.groupCounter},
				{ title: 'Network Slice instances', path: '/nslices', icon:'note',count :this.nsiCounter},
		
			  ];		  
		}
		if(environment.deploymentType=='NSMF' && this.group=="TENANT"){
			this.items = [
				{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
				{ title: 'Network Slice instances', path: '/nslices', icon:'note',count :this.nsiCounter},
		
			  ];		  
		}
		if(environment.deploymentType=='NSMF' && this.group=="TENANT"){
			this.items = [
				{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
				{ title: 'Network Slice instances', path: '/nslices', icon:'note',count :this.nsiCounter},
		
			  ];		  
		}
		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="ADMIN"){		
			this.items = [
			{ title: 'Tenants', path: '/tenant', icon:'person',count :this.tenantsCounter},
			{ title: 'VS descriptors', path: '/vsdescriptor', icon:'note',count :this.vsDescriptorsCounter},
			{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
			{ title: 'Groups', path: '/group', icon:'group',count : this.groupCounter},
			{ title: 'VS Blueprints', path: '/vsblueprint', icon:'note',count :this.vsBlueprintsCounter},
			{ title: 'Network Slice instances', path: '/nslices', icon:'note',count :this.nsiCounter},
			{ title: 'Domain', path: '/domains', icon:'note',count :this.domainCounter}
	
		  ];


		}
		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="TENANT"){		
			this.items = [
			{ title: 'VS descriptors', path: '/vsdescriptor', icon:'note',count :this.vsDescriptorsCounter},
			{ title: 'VS instances', path: '/vsinstance', icon:'note',count :this.vsInstanceCounter},
			{ title: 'VS Blueprints', path: '/vsblueprint', icon:'note',count :this.vsBlueprintsCounter},
			{ title: 'Network Slice instances', path: '/nslices', icon:'note',count :this.nsiCounter},
			{ title: 'Domain', path: '/domains', icon:'note',count :this.domainCounter}
	
		  ];


		}

		if(window.innerWidth <= 400){
			this.breakpoint=1
		}
		else if(window.innerWidth > 400 && window.innerWidth <= 800){
			this.breakpoint=2
		}
		else{
			this.breakpoint=3
		}
		this.getGroup();
		this.getTenant();
		this.getVsDescriptors();
		this.getVsBlueprints();
		this.getVsInstances();
		this.getnetworkSliceInstances();
		this.getDomains();
		//this.getnetworkSliceInstances();
	  }
	  
	  onResize(event) {
		if(event.target.innerWidth <= 400){
			this.breakpoint=1
		}
		else if(event.target.innerWidth > 400 && event.target.innerWidth <= 800){
			this.breakpoint=2
		}
		else{
			this.breakpoint=3
		}
	  }

	  

	  numbers =[1,2,3,4,5,6]
	  getGroup() {
		this.groupService.getGroupData().subscribe(gDatas => {
		  this.groupCounter = gDatas.length;
		  this.counters[3] = gDatas.length;
		});
	  }
	  getTenant() {
		this.groupService.getGroupData().subscribe(gDatas => {
		  this.tenantsCounter = gDatas.length;
		  this.counters[0] = gDatas.length;
		});
	  }
	  getDomains() {
		this.domainsService.getDomainsData().subscribe(dDatas => {
		  this.domainCounter = dDatas.length;
		  this.counters[6] = dDatas.length;
		});
	  }
	  getVsDescriptors() {
		this.vsDescriptorsService.getVsDescriptorsData().subscribe(vsData => {
		  this.vsDescriptorsCounter = vsData.length;
		  this.counters[1] = vsData.length;
		});
	  }
	  getVsInstances() {
		this.vsInstancesService.getVsInstancesData().subscribe((vsInstancesInfos )=> {
		  for(var i=0;i<Object.keys(vsInstancesInfos).length;i++){
	 
			  this.vsInstancesService.getVsInstanceByIdData(vsInstancesInfos[i]).subscribe(res=> {
				i=i+1
			  });
	
		  }
		  this.vsInstanceCounter = i;
		  this.counters[2] = i;
	  });
	}
	  getVsBlueprints() {
		this.vsBlueprintsService.getVsBlueprintsData().subscribe(vsbData => {
		this.vsBlueprintsCounter = vsbData.length;
		this.counters[4] = vsbData.length;
		});
	  }

	  getnetworkSliceInstances() {
		this.nslicesService.getNslicesData().subscribe(nsiData => {
		this.nsiCounter = nsiData.length;
		this.counters[5] = nsiData.length;
		});
	  }
	  
	}
*/

import { VsBlueprintsService } from './../services/vs-Blueprints.service';
import { GroupService } from './../services/group.service';
import { Component, OnInit } from '@angular/core';
import { VsDescriptorsService } from './../services/vs-descriptors.service';
import { VsInstancesService } from './../services/vs-instances.service';
import { NslicesService } from './../services/n-slices.service';
import { DomainsService } from './../services/domains.service';
import { environment } from './../environments/environments';


@Component({
	selector: 'app-dashboard',
	templateUrl: './dashboard.component.html',
	styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

	groupCounter: number = 0;
	tenantsCounter: number = 0;
	vsDescriptorsCounter: number = 0;
	vsInstanceCounter: number = 0;
	vsBlueprintsCounter: number = 0;
	nsiCounter : number = 0;
	nstCounter : number = 0;
	domainCounter : number = 0;
	breakpoint : any;
	group:string;
	items:any;
	counters: number[] = [
		this.tenantsCounter, 
		this.vsDescriptorsCounter, 
		this.vsInstanceCounter, 
		this.groupCounter,
		this.vsBlueprintsCounter,
		this.nsiCounter,
		this.domainCounter,
		this.nstCounter

	];
	constructor(
		private groupService: GroupService,
		private vsDescriptorsService: VsDescriptorsService,
		private vsInstancesService: VsInstancesService,
		private vsBlueprintsService: VsBlueprintsService,
		private nslicesService: NslicesService,
		private domainsService: DomainsService,
		

		){}
	ngOnInit(): void {
		this.group=localStorage.getItem("group");
		console.log("this.group",this.group)
		this.getVsDescriptors();
		this.getVsInstances();
		if(this.group!='TENANT'){
			this.getGroup();
			this.getTenant();
		}

		this.getVsBlueprints();
		this.getnetworkSliceInstances();
		this.getDomains();
		this.getnetworkSliceTemplates()

		
	
		if(environment.deploymentType=='VSMF' && this.group=="ADMIN"){
			this.items = [
				{ title: 'Tenants', path: '/tenant', icon:'person',count :this.tenantsCounter},
				{ title: 'Groups', path: '/group', icon:'group',count : this.groupCounter},
				{ title: 'Domains', path: '/domains', icon:'domain',count :this.domainCounter},
				{ title: 'Vertical Service Blueprints', path: '/vsblueprint', icon:'apps',count :this.vsBlueprintsCounter},
				{ title: 'Vertical Service Descriptors', path: '/vsdescriptor', icon:'description',count :this.vsDescriptorsCounter},
				{ title: 'Network Slice Templates', path: '/nslices', icon:'share',count :this.nsiCounter},
				{ title: 'Vertical Service Instances', path: '/vsinstance', icon:'widgets',count :this.vsInstanceCounter},
			  ];		  
		}
		if(environment.deploymentType=='VSMF' && this.group=="TENANT"){
			this.items = [
				{ title: 'Vertical Service Blueprints', path: '/vsblueprint', icon:'apps',count :this.vsBlueprintsCounter},
				{ title: 'Vertical Service Descriptors', path: '/vsdescriptor', icon:'description',count :this.vsDescriptorsCounter},
				{ title: 'Vertical Service Instances', path: '/vsinstance', icon:'widgets',count :this.vsInstanceCounter}
			  ];		  
		}
		if(environment.deploymentType=='NSMF' && this.group=="ADMIN"){
			this.items = [
				{ title: 'Tenants', path: '/tenant', icon:'person',count :this.tenantsCounter},
				{ title: 'Groups', path: '/group', icon:'group',count : this.groupCounter},
				{ title: 'Network Slice Templates', path: '/share', icon:'note',count :this.nsiCounter},
				{ title: 'Network Slice Instances', path: '/ns-instances', icon:'horizontal_split',count :this.nstCounter}
			  ];		  
		}
		if(environment.deploymentType=='NSMF' && this.group=="TENANT"){
			this.items = [
				{ title: 'Network Slice Templates', path: '/share', icon:'note',count :this.nsiCounter},
				{ title: 'Network Slice Instances', path: '/ns-instances', icon:'horizontal_split',count :this.nstCounter}
		
			  ];		  
		}

		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="ADMIN"){		
			this.items = [
			{ title: 'Tenants', path: '/tenant', icon:'person',count :this.tenantsCounter},
			{ title: 'Groups', path: '/group', icon:'group',count : this.groupCounter},
			{ title: 'Domains', path: '/domains', icon:'domain',count :this.domainCounter},
			{ title: 'Vertical Service Blueprints', path: '/vsblueprint', icon:'apps',count :this.vsBlueprintsCounter},
			{ title: 'Vertical Service Descriptors', path: '/vsdescriptor', icon:'description',count :this.vsDescriptorsCounter},
			{ title: 'Network Slice Templates', path: '/nslices', icon:'share',count :this.nsiCounter},
			{ title: 'Vertical Service Instances', path: '/vsinstance', icon:'widgets',count :this.vsInstanceCounter},
			{ title: 'Network Slice Instances', path: '/ns-instances', icon:'horizontal_split',count :this.nstCounter},
	
		  ];


		}
		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="TENANT"){		
			this.items = [
			{ title: 'Vertical Service Blueprints', path: '/vsblueprint', icon:'apps',count :this.vsBlueprintsCounter},
			{ title: 'Vertical Service Descriptors', path: '/vsdescriptor', icon:'description',count :this.vsDescriptorsCounter},
			{ title: 'Vertical Service Instances', path: '/vsinstance', icon:'widgets',count :this.vsInstanceCounter},
			{ title: 'Network Slice Instances', path: '/ns-instances', icon:'horizontal_split',count :this.nstCounter},	
		  ];


		}

		if(window.innerWidth <= 400){
			this.breakpoint=1
		}
		else if(window.innerWidth > 400 && window.innerWidth <= 800){
			this.breakpoint=2
		}
		else{
			this.breakpoint=3
		}

		//this.getnetworkSliceInstances();
	  }
	  
	  onResize(event) {
		if(event.target.innerWidth <= 400){
			this.breakpoint=1
		}
		else if(event.target.innerWidth > 400 && event.target.innerWidth <= 800){
			this.breakpoint=2
		}
		else{
			this.breakpoint=3
		}
	  }

	  

	  numbers =[0,1,2,3,4,5,6,7]
	  getGroup() {
		this.groupService.getGroupData().subscribe(gDatas => {
				this.groupCounter = gDatas.length;
				if((environment.deploymentType=='VSMF' || 
				environment.deploymentType=='VSMF/NSMF' ||
				environment.deploymentType=='NSMF/VSMF')
				&& this.group=="ADMIN"){
					this.counters[1] = gDatas.length;
				}
				if(environment.deploymentType=='NSMF' && this.group=="ADMIN"){
					this.counters[1] = gDatas.length;
				}				
			

		});
	  }
	  getTenant() {
		  var tenantNumber=0
		this.groupService.getGroupData().subscribe(gDatas => {
		for(var tenantInf of gDatas){
			
			tenantNumber=tenantNumber+Object.keys(tenantInf.tenants).length
		}


		  this.tenantsCounter = tenantNumber;
		  if((environment.deploymentType=='VSMF' || 
		  environment.deploymentType=='VSMF/NSMF' ||
		  environment.deploymentType=='NSMF/VSMF' ||
		  environment.deploymentType=='NSMF')
		  && 
		  this.group=="ADMIN")
		  {
			this.counters[0] = tenantNumber;
		}	
		});
	  }
	  getDomains() {
		this.domainsService.getDomainsData().subscribe(dDatas => {
		  this.domainCounter = dDatas.length;
		  if((environment.deploymentType=='VSMF' || 
		  environment.deploymentType=='VSMF/NSMF' ||
		  environment.deploymentType=='NSMF/VSMF')
		  && 
		  this.group=="ADMIN")
		  {
			this.counters[2] = dDatas.length;
		}	
		});
	  }
	  getVsDescriptors() {
		this.vsDescriptorsService.getVsDescriptorsData().subscribe(vsData => {
		  this.vsDescriptorsCounter = vsData.length;
		  if((environment.deploymentType=='VSMF' || 
		  environment.deploymentType=='VSMF/NSMF' ||
		  environment.deploymentType=='NSMF/VSMF')
		  && 
		  this.group=="ADMIN")
		  {
			this.counters[4] = vsData.length;
		}
		if((environment.deploymentType=='VSMF' || 
		environment.deploymentType=='VSMF/NSMF' ||
		environment.deploymentType=='NSMF/VSMF')
		&& 
		this.group=="TENANT")
		{
		  this.counters[1] = vsData.length;
	  }
		});
	  }
	  getVsInstances() {
		this.vsInstancesService.getVsInstancesData().subscribe((vsInstancesInfos )=> {
		  for(var i=0;i<Object.keys(vsInstancesInfos).length;i++){
	 
			  this.vsInstancesService.getVsInstanceByIdData(vsInstancesInfos[i]).subscribe(res=> {
				i=i+1
			  });
	
		  }
		  this.vsInstanceCounter = i;
		  if(environment.deploymentType=='VSMF' && this.group=="ADMIN"){
			  this.counters[6] = i;
		  }
		  if(environment.deploymentType=='VSMF' && this.group=="TENANT"){
			this.counters[2] = i;
		}
		  if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="TENANT"){
			  this.counters[2] = i;
		  }
		  
		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="ADMIN"){
			this.counters[6] = i;

		}
		  if(environment.deploymentType=='NSMF' && this.group=="TENANT"){
			this.counters[1] = i;
		  }
	  });
	}
	  getVsBlueprints() {
		this.vsBlueprintsService.getVsBlueprintsData().subscribe(vsbData => {
		this.vsBlueprintsCounter = vsbData.length;
		if((environment.deploymentType=='VSMF' || 
		environment.deploymentType=='VSMF/NSMF' ||
		environment.deploymentType=='NSMF/VSMF')
		&& 
		this.group=="ADMIN")
		{
		  this.counters[3] = vsbData.length;
	  }
	  if((environment.deploymentType=='VSMF' || 
	  environment.deploymentType=='VSMF/NSMF' ||
	  environment.deploymentType=='NSMF/VSMF')
	  && 
	  this.group=="TENANT")
	  {
		this.counters[0] = vsbData.length;
	}
		});
	  }
	  
	  getnetworkSliceTemplates() {
		this.nslicesService.getNslicesData().subscribe(nsiData => {
		this.nsiCounter = nsiData.length;
		if(environment.deploymentType=='VSMF' && this.group=="ADMIN")
		{
		  this.counters[5] = nsiData.length;
		}
		if(environment.deploymentType=='NSMF' && this.group=="ADMIN")
		{
		  this.counters[2] = nsiData.length;
		}
		if(environment.deploymentType=='NSMF' && this.group=="TENANT")
		{
		  this.counters[0] = nsiData.length;
		}
		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="ADMIN"){
			this.counters[5] = nsiData.length;
		}

		});
	  }
	  


	  
	  getnetworkSliceInstances() {
		  this.nstCounter=0;
		  if(environment.deploymentType=='NSMF' && this.group=="ADMIN")
		  {
			this.counters[3] = 0;
		  }
		if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && this.group=="ADMIN"){
			this.counters[7] = 0;	
			this.nstCounter=0;
		}
		if(((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') || environment.deploymentType=='NSMF') && this.group=="TENANT"){
			this.counters[3] = 0;	
			this.nstCounter=0;
		}	
	  }
	}
