import { Router } from '@angular/router';
import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { VsInstanceInfo } from '../vs-instance/vs-instance-info';
import { DOCUMENT } from '@angular/common';
import { VsInstancesService } from '../../../services/vs-instances.service';
import { VsDescriptorsService } from '../../../services/vs-descriptors.service';
import {VsDescriptorInfo} from '../vs-descriptor/vs-descriptor-info'
import { VsBlueprintsService } from '../../../services/vs-Blueprints.service';

@Component({
  selector: 'app-vs-instance-stepper',
  templateUrl: './vs-instance-stepper.component.html',
  styleUrls: ['./vs-instance-stepper.component.css']
})
export class VsInstanceStepperComponent implements OnInit {

  firstFormGroup: FormGroup;
  vsInstanceInfo: VsInstanceInfo[] = [];
  configurableParameters=[];
  cpArr=[];
  vsdInfos= [];
  vsDescriptorInfos: VsDescriptorInfo[] = [];
  showConfigure=false;
  panelOpenState = false;
 
  constructor(@Inject(DOCUMENT) private document,
    private _formBuilder: FormBuilder,
    private vsInstancesService:VsInstancesService,
    private router: Router,
    private vsDescriptorsService:VsDescriptorsService,
    private vsBlueprintsService:VsBlueprintsService

    
) { }

  ngOnInit() {
    this.getVsDescriptor();
    this.firstFormGroup = this._formBuilder.group({
      name: ['', Validators.required],
      description: ['', Validators.required],
      vsdId : ['', Validators.required],
      tenantId : ['', Validators.required],
    });
    
  }

  getVsDescriptor() {
    this.vsDescriptorsService.getVsDescriptorsData().subscribe((vsDescriptorInfos : VsDescriptorInfo[] )=> {
    this.vsDescriptorInfos = vsDescriptorInfos;
     for(var vs of this.vsDescriptorInfos) {
      this.vsdInfos.push(vs)
     }

  });
  }
  onOptionsSelected($event){

    this.vsDescriptorsService.getVsDescriptorsByIdData($event.value).subscribe((vsDescriptorInfo : VsDescriptorInfo[] )=> {
    //  this.vsDescriptorInfos = vsDescriptorInfos;
    this.vsBlueprintsService.getVsBlueprint(vsDescriptorInfo['vsBlueprintId']).subscribe((vsbInf )=> {
      if(vsbInf!=undefined){
        this.showConfigure=true;
        this.configurableParameters=vsbInf['vsBlueprint']['configurableParameters']
      }else{
        this.showConfigure=false;
      }
    });
  
    });
    

  }
  selectedConfigureParam($event,cp){
    this.cpArr.push({name:cp,value:$event.originalTarget.value})


  }
createOnBoardVsInstanceRequest() {
  var onBoardVsInstRequest = JSON.parse('{}');
  var onBoardcpRequest = JSON.parse('{}');
  for(var cp of this.cpArr){
    onBoardcpRequest[cp['name']]=cp['value']
  }

    onBoardVsInstRequest['name'] = this.firstFormGroup.get('name').value;
    onBoardVsInstRequest['description'] = this.firstFormGroup.get('description').value;
    onBoardVsInstRequest['vsdId'] = this.firstFormGroup.get('vsdId').value;
    onBoardVsInstRequest['tenantId'] = localStorage.getItem('username');
    onBoardVsInstRequest['userData'] = JSON.parse('{}');
    onBoardVsInstRequest['userData']=onBoardcpRequest;

    this.vsInstancesService.postVsInstanceData(onBoardVsInstRequest)
      .subscribe(
        res => {
        //console.log("Successfully uploaded new vs Descriptor with id " + vsDescriptortId);
       this.router.navigate(['/vsinstance']);
      });
       
      
  }
}
