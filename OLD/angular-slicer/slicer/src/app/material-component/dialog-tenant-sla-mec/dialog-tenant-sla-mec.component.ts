import { Component, OnInit,Inject } from '@angular/core';
import { MatDialogRef,MAT_DIALOG_DATA } from '@angular/material';
import { TenantService } from '../../services/tenant.service';
import { FormArray,FormGroup,FormBuilder,Validators } from '@angular/forms';

@Component({
  selector: 'app-dialog-tenant-sla-mec',
  templateUrl: './dialog-tenant-sla-mec.component.html',
  styleUrls: ['./dialog-tenant-sla-mec.component.css']
})
export class DialogTenantSlaMecComponent implements OnInit {
  constructor(public dialogRef: MatDialogRef<DialogTenantSlaMecComponent>,
    private tenantService:TenantService,
    private _formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) { }
  formGroup: FormGroup;
  items: FormArray;
  selectedAdmin : string;
  selected: 'admin';
  tenant =this.data['dialUsername']

  ngOnInit() {
    this.dialogRef.updatePosition({ top: `5%`,
    right: `40%`});
    this.formGroup = this._formBuilder.group({
      slaStatus: ['', Validators.required],
      memoryRAM: ['', Validators.required],
      vCPU: ['', Validators.required],
      diskStorage: ['', Validators.required],
      location :['', Validators.required],
      items: this._formBuilder.array([this.createItem()])
    });
    this.items = this.formGroup.get('items') as FormArray;
  }
  addMecSla(slaStatus){
    var meclatRequest = JSON.parse('{}');
    var status =(<HTMLInputElement>document.getElementById(slaStatus)).value;
    //var scp =(<HTMLInputElement>document.getElementById(scope)).value;
    meclatRequest['tenant']=this.data['dialUsername'];
    meclatRequest['slaStatus']=status;
    meclatRequest['slaConstraints']=[];
    console.log("this.items.value",this.items)

    for(var it of this.items.value){
      meclatRequest['slaConstraints'].push(
        {'scope':'MEC_RESOURCE',
        'location':it['location'],
        'maxResourceLimit':{'memoryRAM':it['memoryRAM'],'vCPU':it['vCPU'],'diskStorage':it['diskStorage']}
      });
    }
        this.tenantService.postSlaData(meclatRequest,this.data['dialGroup'],this.data['dialUsername']).subscribe(respId => console.log("mec reponse id : " + respId))
  }

  addItem(): void {
    this.items = this.formGroup.get('items') as FormArray;
    this.items.push(this.createItem());
  }

  removeItem() {
    this.items = this.formGroup.get('items') as FormArray;
    this.items.removeAt(this.items.length - 1);
  }

  createItem(): FormGroup {
    return this._formBuilder.group({
      location: '',
      memoryRAM: '',
      vCPU: '',
      diskStorage: ''
    });
  }
}
