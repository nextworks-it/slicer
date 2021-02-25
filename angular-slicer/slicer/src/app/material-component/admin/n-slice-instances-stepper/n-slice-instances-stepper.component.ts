
import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, FormArray, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { NslicesService } from '../../../services/n-slices.service';
import { NsliceInstancesService } from '../../../services/n-slice-instances.service';

import { NslicesInfo } from '../n-slices/n-slices-info';

@Component({
  selector: 'app-n-slice-instances-stepper',
  templateUrl: './n-slice-instances-stepper.component.html',
  styleUrls: ['./n-slice-instances-stepper.component.css']
})
export class NSliceInstancesStepperComponent implements OnInit {

  isLinear = true;
  nslicesInfos: NslicesInfo[] = [];  
  selected: string;
  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  nsiId:String;
  constructor(private router: Router, 
    private _formBuilder: FormBuilder,
    private nslicesService:NslicesService,
    private nsliceInstancesService:NsliceInstancesService,
    ) {
  }

  ngOnInit() {
    this.selected='Nst Id'
    this.getNslices();
    this.firstFormGroup = this._formBuilder.group({
      nstId: [''],
      name: [''],
      description: [''],
    });

    this.secondFormGroup = this._formBuilder.group({
      nsiId: [''],
      dfId: [''],
      instantiationLevelId: [''],
      latitude: [''],
      longitude: [''],
      altitude: [''],
      range: [''],
      ranEndPointId:['']
     });

  
  }
  getNslices() {
    this.nslicesService.getNslicesData().subscribe((nslicesInfos : NslicesInfo[] )=> {
    this.nslicesInfos = nslicesInfos;
  });
} 

createNsi(){
  var onBoardNsiRequest = JSON.parse('{}');
  onBoardNsiRequest['nstUuid']=this.firstFormGroup.get('nstId').value;
  onBoardNsiRequest['name']=this.firstFormGroup.get('name').value;
  onBoardNsiRequest['description']=this.firstFormGroup.get('description').value;
  this.nsliceInstancesService.postNsliceInstancesData(onBoardNsiRequest)
  .subscribe(nsiId => {
    this.nsiId=nsiId;
  });
}


createNsiWithDetails(){
  var onBoardNsidRequest = JSON.parse('{}');
  onBoardNsidRequest['locationConstraints'] = JSON.parse('{}');
  onBoardNsidRequest['nstId']=this.firstFormGroup.get('nstId').value;
  onBoardNsidRequest['nsiId']=this.nsiId
  onBoardNsidRequest['dfId']=this.secondFormGroup.get('dfId').value;
  onBoardNsidRequest['ilId']=this.secondFormGroup.get('instantiationLevelId').value;
  onBoardNsidRequest['locationConstraints']['latitude']=this.secondFormGroup.get('latitude').value;
  onBoardNsidRequest['locationConstraints']['longitude']=this.secondFormGroup.get('longitude').value;
  onBoardNsidRequest['locationConstraints']['altitude']=this.secondFormGroup.get('altitude').value;
  onBoardNsidRequest['locationConstraints']['range']=this.secondFormGroup.get('range').value;
  onBoardNsidRequest['ranEndPointId']=this.secondFormGroup.get('ranEndPointId').value;
  console.log("onBoardNsidRequest",onBoardNsidRequest);
  this.nsliceInstancesService.postNsliceInstancesInstantiateData(onBoardNsidRequest,this.nsiId)
  .subscribe(_ => {
    this.router.navigate(['/ns-instances']);
  });
  
}
/*
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


   
}
*/


}