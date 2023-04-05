/*
import { Injectable } from '@angular/core';

export interface Menu {
  state: string;
  name: string;
  type: string;
  icon: string;
}

const MENUITEMS = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'tenant', type: 'link', name: 'Tenants', icon: 'person' },
  { state: 'group', type: 'link', name: 'Groups', icon: 'group' },
  { state: 'domains', type: 'link', name: 'Domains', icon: 'domain' },
  //{ state: '', type: 'sublink', name: 'Domains', icon: 'domain' },
  { state: 'vsblueprint', type: 'link', name: 'Vertical Service Blueprints', icon: 'view_headline' },
  { state: 'vsdescriptor', type: 'link', name: 'Vertical Service Descriptors', icon: 'description' },
  //{ state: '', type: 'link', name: 'NS Catalogue', icon: 'receipt' },
  { state: 'nslices', type: 'link', name: 'Network Slice Templates', icon: 'list_alt' },
  { state: 'vsinstance', type: 'link', name: 'Vertical Service Instances', icon: 'tab' }
];

@Injectable()
export class MenuItems {
  getMenuitem(): Menu[] {
    return MENUITEMS;
  }
}

*/
import { environment } from '../../environments/environments';
import { Injectable } from '@angular/core';
export interface Menu {
  state: string;
  name: string;
  type: string;
  icon: string;
}
const menuVsmfAdmin = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'tenant', type: 'link', name: 'Tenants', icon: 'person' },
  { state: 'group', type: 'link', name: 'Groups', icon: 'group' },
  { state: 'domains', type: 'link', name: 'Domains', icon: 'domain' },
  { state: 'vsblueprint', type: 'link', name: 'Vertical Service Blueprints', icon: 'apps' },
  { state: 'vsdescriptor', type: 'link', name: 'Vertical Service Descriptors', icon: 'description' },
  { state: 'nslices', type: 'link', name: 'Network Slice Templates', icon: 'share' },
  { state: 'vsinstance', type: 'link', name: 'Vertical Service Instances', icon: 'widgets' }
];
const menuVsmfTenant = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'vsblueprint', type: 'link', name: 'Vertical Service Blueprints', icon: 'apps' },
  { state: 'vsdescriptor', type: 'link', name: 'Vertical Service Descriptors', icon: 'description' },
  { state: 'vsinstance', type: 'link', name: 'Vertical Service Instances', icon: 'widgets' }
];

const menuNsmfAdmin = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'tenant', type: 'link', name: 'Tenants', icon: 'person' },
  { state: 'group', type: 'link', name: 'Groups', icon: 'group' },
  { state: 'nslices', type: 'link', name: 'Network Slice Templates', icon: 'share' },
  { state: '', type: 'link', name: 'Network Slice Instances', icon: 'horizontal_split' }
];

const menuNsmfTenant = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'nslices', type: 'link', name: 'Network Slice Templates', icon: 'share' },
  { state: 'ns-instances', type: 'link', name: 'Network Slice Instances', icon: 'horizontal_split' }
];

const menuVsmfNsmfAdmin = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'tenant', type: 'link', name: 'Tenants', icon: 'person' },
  { state: 'group', type: 'link', name: 'Groups', icon: 'group' },
  { state: 'domains', type: 'link', name: 'Domains', icon: 'domain' },
  { state: 'vsblueprint', type: 'link', name: 'Vertical Service Blueprints', icon: 'apps' },
  { state: 'vsdescriptor', type: 'link', name: 'Vertical Service Descriptors', icon: 'description' },
  { state: 'nslices', type: 'link', name: 'Network Slice Templates', icon: 'share' },
  { state: 'vsinstance', type: 'link', name: 'Vertical Service Instances', icon: 'widgets' },
  { state: 'ns-instances', type: 'link', name: 'Network Slice Instances', icon: 'horizontal_split' }

];

const menuVsmfNsmfTenant = [
  { state: 'dashboard', name: 'Home', type: 'link', icon: 'home' },
  { state: 'vsblueprint', type: 'link', name: 'Vertical Service Blueprints', icon: 'apps' },
  { state: 'vsdescriptor', type: 'link', name: 'Vertical Service Descriptors', icon: 'description' },
  { state: 'vsinstance', type: 'link', name: 'Vertical Service Instances', icon: 'widgets' },
  { state: 'ns-instances', type: 'link', name: 'Network Slice Instances', icon: 'horizontal_split' }

];
const group = localStorage.getItem("group")

@Injectable()
export class MenuItems {
  getMenuitem(): Menu[] {
    if(environment.deploymentType=='VSMF' && group=="ADMIN"){
      return menuVsmfAdmin;
    }
    if(environment.deploymentType=='VSMF' && group=="TENANT"){
      return menuVsmfTenant;
    }
    if(environment.deploymentType=='NSMF' && group=="ADMIN"){
      return menuNsmfAdmin;
    }    
    if(environment.deploymentType=='NSMF' && group=="TENANT"){
      return menuNsmfTenant;
    }
    if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF') && group=="ADMIN"){
      return menuVsmfNsmfAdmin;
    }    
    if((environment.deploymentType=='VSMF/NSMF' || environment.deploymentType=='NSMF/VSMF')  && group=="TENANT"){
      return menuVsmfNsmfTenant;
    }   
  }
}
