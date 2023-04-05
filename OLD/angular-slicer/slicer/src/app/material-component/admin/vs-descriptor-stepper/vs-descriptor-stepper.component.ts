import { VsBlueprintsService } from './../../../services/vs-Blueprints.service';
import { GroupInfo} from '../group/group-info';
import { Router } from '@angular/router';
import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { VsBlueprintInfo } from '../vs-blueprint/vs-blueprint-info';
import { DOCUMENT } from '@angular/common';
import { VsDescriptorsService } from '../../../services/vs-descriptors.service';
import { VsBlueprint } from '../../admin/vs-blueprint/vs-blueprint';

@Component({
  selector: 'app-vs-descriptor-stepper',
  templateUrl: './vs-descriptor-stepper.component.html',
  styleUrls: ['./vs-descriptor-stepper.component.css']
})
export class VsDescriptorStepperComponent implements OnInit {

  firstFormGroup: FormGroup;
  vsBlueprintInfos: VsBlueprintInfo[] = [];
  vsBlueprint: VsBlueprint = new VsBlueprint();
  
  managementTypes: String[] = [
    "PROVIDER_MANAGED",
    "TENANT_MANAGED"
  ];

  ssTypes: String[] = [
    "NONE",
    "EMBB",
    "URLLC",
    "M_IOT",
    "ENTERPRISE",
    "NFV_IAAS"
  ];

  panelOpenState = false;
  groupInfos: GroupInfo[] = [];
  constructor(@Inject(DOCUMENT) private document,
    private _formBuilder: FormBuilder,
    private vsDescriptorsService:VsDescriptorsService,
    private blueprintsVsService: VsBlueprintsService,
    private router: Router
) { }

  ngOnInit() {   
    this.getVsBlueprints();
    this.firstFormGroup = this._formBuilder.group({
      blueprintId: ['', Validators.required],
      descName: ['', Validators.required],
      descVersion: ['', Validators.required],
      descSType : ['', Validators.required],
      managementType : ['', Validators.required],
      qosParam: [''],
      isPublic: [false],
    });
  }

  getVsBlueprints(): void {
    this.blueprintsVsService.getVsBlueprintsData().subscribe((vsBlueprintInfos: VsBlueprintInfo[]) => 
      { 
        this.vsBlueprintInfos = vsBlueprintInfos;
        //console.log(this.vsBlueprintInfos);
        for(var inf of vsBlueprintInfos){
          this.getVsBlueprint(inf['vsBlueprintId'])
        }
      });
  }

  getVsBlueprint(vsBlueprintId: string) {
    this.blueprintsVsService.getVsBlueprint(vsBlueprintId).subscribe((vsBlueprintInfo: VsBlueprintInfo) =>
      {
        this.vsBlueprint = vsBlueprintInfo['vsBlueprint'];
        //console.log(this.vsBlueprint);
      });
  }

  onExpBSelected(event: any) {
    var selectedBlueprint = event.value;
    //console.log(selectedBlueprint);
    var vsbId;
    for (var i = 0; i < this.vsBlueprintInfos.length; i ++) {
      if (this.vsBlueprintInfos[i]['vsBlueprintId'] == selectedBlueprint) {
        vsbId = this.vsBlueprintInfos[i]['vsBlueprintId'];
      }
    }
    this.getVsBlueprint(vsbId);
  }

  createOnBoardVsDescriptorRequest() {
    var onBoardVsDescRequest = JSON.parse('{}');
    onBoardVsDescRequest['vsd'] = {};
    onBoardVsDescRequest['vsd']['name'] = this.firstFormGroup.get('descName').value;
    onBoardVsDescRequest['vsd']['version'] = this.firstFormGroup.get('descVersion').value;
    onBoardVsDescRequest['vsd']['sst'] = this.firstFormGroup.get('descSType').value;
    onBoardVsDescRequest['vsd']['vsBlueprintId'] = this.firstFormGroup.get('blueprintId').value;
    onBoardVsDescRequest['vsd']['managementType'] = this.firstFormGroup.get('managementType').value;
    
    var qosParameters = {};

    if(this.vsBlueprint['parameters'] !== undefined){
      for (var i = 0; i < this.vsBlueprint['parameters'].length; i++) {
        qosParameters[this.vsBlueprint['parameters'][i]['parameterId']] =
          this.document.getElementById('qos_' + this.vsBlueprint['parameters'][i]['parameterId']).value;
      }
      onBoardVsDescRequest['vsd']['qosParameters'] = qosParameters;
    }

    onBoardVsDescRequest['vsd']['serviceConstraints']=[];
    onBoardVsDescRequest['vsd']['serviceConstraints'].push(
      {"sharable":"","canIncludeSharedElements":"","priority":'LOW'}
      );
    onBoardVsDescRequest['vsd']['sla']=JSON.parse('{}');
    onBoardVsDescRequest['vsd']['sla']['serviceCreationTime']='SERVICE_CREATION_TIME_MEDIUM';
    onBoardVsDescRequest['vsd']['sla']['availabilityCoverage']='AVAILABILITY_COVERAGE_MEDIUM';
    onBoardVsDescRequest['vsd']['sla']['lowCostRequired']="";

    onBoardVsDescRequest['tenantId'] =localStorage.getItem('username');
    onBoardVsDescRequest['isPublic']=this.firstFormGroup.get('isPublic').value;

    this.vsDescriptorsService.postVsDescriptorsData(onBoardVsDescRequest)
      .subscribe(
        vsDescriptortId => {
        //console.log("Successfully uploaded new vs Descriptor with id " + vsDescriptortId);
        this.router.navigate(['/vsdescriptor']);
      })
  }
}
