
import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { DOCUMENT } from '@angular/common';
import { NslicesService } from './../../services/n-slices.service';
import { Router } from '@angular/router';
import { environment } from './../../environments/environments';
@Component({
  selector: 'app-n-slices-stepper',
  templateUrl: './n-slices-stepper.component.html',
  styleUrls: ['./n-slices-stepper.component.css']
})
export class NSlicesStepperComponent implements OnInit {

  firstFormGroup: FormGroup;
  secondFormGroup: FormGroup;
  thirdFormGroup: FormGroup;
  vnfObj:Object;
  nstObj: Object;
  nsdObj: Object;
  showNsd:boolean;
  nsmfMode:boolean;
  vsmfMode:boolean;
  isLinear = true;
  constructor(@Inject(DOCUMENT) private document,
    private _formBuilder: FormBuilder,
    private nslicesService:NslicesService,
    private router: Router
) { }

  ngOnInit() {
    if(environment.deploymentType=='VSMF/NSMF' || 
        environment.deploymentType=='NSMF/VSMF' ||
        environment.deploymentType=='NSMF'
        ){
          this.showNsd=true;
    }
    if(environment.deploymentType=='NSMF'){
      this.nsmfMode=true
    }
    if(environment.deploymentType=='VSMF'){
      this.vsmfMode=true
    }  
   this.firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required],

  });
  this.secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],

  });
  this.thirdFormGroup = this._formBuilder.group({
    thirdCtrl: ['', Validators.required],

  });
  }
  onUploadedVnfP(event: any, vnfp: File[]) {
    let promises = [];
  
    for (let vnf of vnfp) {
        let vnfPromise = new Promise(resolve => {
            let reader = new FileReader();
            reader.readAsText(vnf);
            reader.onload = () => resolve(reader.result);
        });
        promises.push(vnfPromise);
    }
  
    Promise.all(promises).then(fileContents => {
        this.vnfObj = JSON.parse(fileContents[0]);
  
    });
  }

  onUploadedNst(event: any, nsts: File[]) {

    let promises = [];

    for (let nst of nsts) {
        let nstPromise = new Promise(resolve => {
            let reader = new FileReader();
            reader.readAsText(nst);
            reader.onload = () => resolve(reader.result);
        });
        promises.push(nstPromise);
    }

    Promise.all(promises).then(fileContents => {
        this.nstObj = JSON.parse(fileContents[0]);

        //console.log(JSON.stringify(this.nstObj, null, 4));
    });
  }

  onUploadedNsd(event: any, nsds: File[]) {

    let promises = [];

    for (let nsd of nsds) {
        let nsdPromise = new Promise(resolve => {
            let reader = new FileReader();
            reader.readAsText(nsd);
            reader.onload = () => resolve(reader.result);
        });
        promises.push(nsdPromise);
    }

    Promise.all(promises).then(fileContents => {
        this.nsdObj = JSON.parse(fileContents[0]);

        //console.log(JSON.stringify(this.nstObj, null, 4));
    });
  }

  createOnBoardNstRequest() {
    var onBoardNsRequest = JSON.parse('{}');
    onBoardNsRequest['nst']=this.nstObj
    if(this.nsdObj!=null){
      onBoardNsRequest['nsds']=this.nsdObj
    }
    if(this.vnfObj!=null){
      onBoardNsRequest['vnfPackages']=this.vnfObj
    }

    this.nslicesService.postNslicesData(onBoardNsRequest)
      .subscribe(
        nst => {
        console.log("Successfully uploaded new nst " + nst);
        this.router.navigate(['/nslices']);
      })   
      
      
    }

   
}
