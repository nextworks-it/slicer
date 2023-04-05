import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import {STEPPER_GLOBAL_OPTIONS} from '@angular/cdk/stepper';
import { DomainsService } from './../../../services/domains.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-domains-wizard',
  templateUrl: './domains-wizard.component.html',
  styleUrls: ['./domains-wizard.component.css'],
	providers: [{
	    provide: STEPPER_GLOBAL_OPTIONS, useValue: {displayDefaultIndicatorType: false}
	}]
})
export class DomainsWizardComponent implements OnInit {
  domainInterface = [];
  domainInfos = [];
  typeValues=[];
  isLinear = true;
  items: FormArray;
  //domainTypes =['NH', 'Nfvo', 'Nsp', 'Osm', 'Sonata', 'Vertical'];
  domainTypes =['NH', 'Osm', 'Sonata'];

  selected: string;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  fourthFormGroup:FormGroup;
  filterElement: string;
  constructor(private router: Router, private _formBuilder: FormBuilder,private domainsService:DomainsService) {
  }

  ngOnInit() {

    //this.selected='NH';
    this.filterElement=this.selected;

    this.firstFormGroup = this._formBuilder.group({
      domainId: [''],
      name: [''],
      description: [''],
      owner: [''],
      admin: [''],
      domainStatus: [''],
    });

    this.secondFormGroup = this._formBuilder.group({
      url: [''],
      port: [''],
      auth: [''],
      interfaceType: ['']
     });

    this.thirdFormGroup = this._formBuilder.group({
      type: [''],
      items: this._formBuilder.array([this.createItem()])
    });

    this.fourthFormGroup = this._formBuilder.group({
      sonataDomainLayerId: ['', Validators.required],
      sonataDomainLayerType: ['', Validators.required],
      sonataNspNbiType: ['', Validators.required],
      sonataUsername: ['', Validators.required],
      sonataPassword: ['', Validators.required],
      sonataRanenabled: ['', Validators.required],
      nhDomainLayerId: ['', Validators.required],
      nhDomainLayerType: ['', Validators.required],
      nhNspNbiType: ['', Validators.required],
      nhUserId: ['', Validators.required],
      nhTenantId: ['', Validators.required],
      nhRanenabled: ['', Validators.required],
      osmDomainLayerId: ['', Validators.required],
      osmDomainLayerType: ['', Validators.required],
      osmManoNbiType: ['', Validators.required],
      osmUsername: ['', Validators.required],
      osmPassword: ['', Validators.required],
      osmProject: ['', Validators.required],
      items: this._formBuilder.array([this.createItem()])
    });

  }

  createItem(): FormGroup {
    return this._formBuilder.group({
      type: '',
    });
  }



  createDomains(){
    var onBoardDsRequest = JSON.parse('{}');
    var mainOnBoardRequest=[];
    var domainId = this.firstFormGroup.get('domainId').value;
    var domainName = this.firstFormGroup.get('name').value;
    var domainDescription = this.firstFormGroup.get('description').value;
    var domainOwner = this.firstFormGroup.get('owner').value;
    var domainAdmin = this.firstFormGroup.get('admin').value;
    var domainStatus = this.firstFormGroup.get('domainStatus').value;
 
    onBoardDsRequest['domainId'] = domainId;
    onBoardDsRequest['name'] = domainName;
    onBoardDsRequest['description'] = domainDescription;
    onBoardDsRequest['owner'] = domainOwner;
    onBoardDsRequest['admin'] = domainAdmin;
    onBoardDsRequest['domainStatus'] = domainStatus;

   // onBoardDsRequest['domainInterface'] = [];
    var Interface = JSON.parse('{}');
    var url = this.secondFormGroup.get('url').value;
    var port = this.secondFormGroup.get('port').value;
    var auth = this.secondFormGroup.get('auth').value;
    var interfaceType = this.secondFormGroup.get('interfaceType').value;

    Interface['url'] = url;
    Interface['port'] = port;
    Interface['auth'] = auth;
    Interface['interfaceType'] = interfaceType;
    //onBoardDsRequest.domainInterface.push(Interface);
    onBoardDsRequest['domainInterface']=Interface


    onBoardDsRequest['ownedLayers'] = [];

    /*************sonata*******************/
    var sonata = JSON.parse('{}');
    var sDomainLayerId = this.fourthFormGroup.get('sonataDomainLayerId').value;
    var sDomainLayerType = this.fourthFormGroup.get('sonataDomainLayerType').value;
    var sNspNbiType = this.fourthFormGroup.get('sonataNspNbiType').value;
    var sUsername = this.fourthFormGroup.get('sonataUsername').value;
    var sPassword = this.fourthFormGroup.get('sonataPassword').value;
    var sRanenabled = this.fourthFormGroup.get('sonataRanenabled').value;
    if(sDomainLayerId !=""){
    sonata['type']="Sonata";
    sonata['domainLayerId']=sDomainLayerId;
    sonata['domainLayerType']='NETWORK_SLICE_PROVIDER';
    sonata['nspNbiType']='SONATA';
    sonata['username']=sUsername;
    sonata['password']=sPassword;
    sonata['ranenabled']=sRanenabled;
    onBoardDsRequest['ownedLayers'].push(sonata);

    }
    /*************NH*******************/
    var nh = JSON.parse('{}');
    var nDomainLayerId = this.fourthFormGroup.get('nhDomainLayerId').value;
    var nDomainLayerType = this.fourthFormGroup.get('nhDomainLayerType').value;
    var nNspNbiType = this.fourthFormGroup.get('nhNspNbiType').value;
    var nUserId = this.fourthFormGroup.get('nhUserId').value;
    var nTenantId = this.fourthFormGroup.get('nhTenantId').value;
    var nRanenabled = this.fourthFormGroup.get('nhRanenabled').value;
    if(nDomainLayerId !=""){
    nh['type']="NH";
    nh['domainLayerId']=nDomainLayerId;
    nh['domainLayerType']='NETWORK_SLICE_PROVIDER';
    nh['nspNbiType']='NEUTRAL_HOSTING';
    nh['userId']=nUserId;
    nh['tenantId']=nTenantId;
    nh['ranenabled']=nRanenabled;
    onBoardDsRequest['ownedLayers'].push(nh);
    }

    /*************Osm*******************/
    var osm = JSON.parse('{}');
    var oDomainLayerId = this.fourthFormGroup.get('osmDomainLayerId').value;
    var oDomainLayerType = this.fourthFormGroup.get('osmDomainLayerType').value;
    var oManoNbiType = this.fourthFormGroup.get('osmManoNbiType').value;
    var oUsername = this.fourthFormGroup.get('osmUsername').value;
    var oPassword = this.fourthFormGroup.get('osmPassword').value;
    var oProject = this.fourthFormGroup.get('osmProject').value;
    if(oDomainLayerId !=""){
      osm['type']="Osm";
      osm['domainLayerId']=oDomainLayerId;
      osm['domainLayerType']=oDomainLayerType;
      osm['manoNbiType']='OSM_DRIVER';
      osm['username']=oUsername;
      osm['password']=oPassword;
      osm['project']=oProject;
      onBoardDsRequest['ownedLayers'].push(osm);
    }

    this.domainsService.postDomain(onBoardDsRequest)
    .subscribe(res => {
      this.router.navigate(['/domains']);
    });
    
}
getValues(event: {
  isUserInput: any;
  source: { value: any; selected: any };
}) {
  if (event.isUserInput) {
    if (event.source.selected === true) {
      this.typeValues.push(event.source.value)
     
    } else {
      const index: number = this.typeValues.indexOf(event.source.value);
      this.typeValues.splice(index, 1);
    }
  }
}


}