import 'hammerjs';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { DemoMaterialModule } from '../demo-material-module';
import { CdkTableModule } from '@angular/cdk/table';

import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';

import { MaterialRoutes } from './material.routing';
import {
  DialogGroupComponent,
} from './admin/dialog-group/dialog-group.component';
import { AdminComponent } from './admin/admin.component';
import { VsDescriptorComponent } from './admin/vs-descriptor/vs-descriptor.component';
import { VsInstanceComponent } from './admin/vs-instance/vs-instance.component';
import { BlueprintDetailsComponent } from './admin/blueprint-details/blueprint-details.component';
import { VsBlueprintComponent } from './admin/vs-blueprint/vs-blueprint.component';
import { NSlicesComponent } from './admin/n-slices/n-slices.component';
//import { GroupComponent } from './admin/group/group.component';
import { SlaComponent } from './admin/sla/sla.component';
import {AdminTenantComponent} from './admin/admintenant/admin-tenant.component';
import { DialogTenantComponent } from './admin/dialog-tenant/dialog-tenant.component';
import { VsBlueprintStepperComponent } from './admin/vs-blueprint-stepper/vs-blueprint-stepper.component';
import { DialogTenantSlaMecComponent } from './dialog-tenant-sla-mec/dialog-tenant-sla-mec.component';
import { DialogTenantSlaCloudComponent } from './dialog-tenant-sla-cloud/dialog-tenant-sla-cloud.component';
import { DialogTenantSlaGlobalComponent } from './dialog-tenant-sla-global/dialog-tenant-sla-global.component';
import { DomainsComponent } from './domains/domains.component';
import { DomainsDetailsComponent } from './domains-details/domains-details.component';
import { DomainsWizardComponent } from './admin/domains-wizard/domains-wizard.component';
import { VsDescriptorStepperComponent } from './admin/vs-descriptor-stepper/vs-descriptor-stepper.component';
import { NSlicesStepperComponent } from './n-slices-stepper/n-slices-stepper.component';
import { SecondLoginComponent } from './second-login/second-login.component';
import{VsInstanceDetailsComponent} from './vs-instance-details/vs-instance-details.component';
import { VsInstanceStepperComponent } from './admin/vs-instance-stepper/vs-instance-stepper.component';
import { VsBlueprintDetailsComponent } from './admin/vs-blueprint-details/vs-blueprint-details.component';
import { BlueprintGraphComponent } from './admin/blueprint-graph/blueprint-graph.component';
import { NSliceInstancesComponent } from './admin/n-slice-instances/n-slice-instances.component';
import { NSliceInstancesStepperComponent } from './admin/n-slice-instances-stepper/n-slice-instances-stepper.component'
@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(MaterialRoutes),
    DemoMaterialModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    FlexLayoutModule,
    CdkTableModule
  ],
  providers: [],
  entryComponents: [],
  declarations: [
    DialogGroupComponent,
    AdminComponent,
    VsDescriptorComponent,
    VsInstanceComponent,
    BlueprintDetailsComponent,
    VsBlueprintComponent,
    NSlicesComponent,
    //GroupComponent,
    SlaComponent,
    AdminTenantComponent,
    DialogTenantComponent,
    VsBlueprintStepperComponent,
    DialogTenantSlaMecComponent,
    DialogTenantSlaCloudComponent,
    DialogTenantSlaGlobalComponent,
    DomainsComponent,
    VsInstanceDetailsComponent,
    DomainsDetailsComponent,
    DomainsWizardComponent,
    VsDescriptorStepperComponent,
    NSlicesStepperComponent,
    SecondLoginComponent,
    VsInstanceStepperComponent,
    VsBlueprintDetailsComponent,
    BlueprintGraphComponent,
    NSliceInstancesComponent,
    NSliceInstancesStepperComponent
  ]
})
export class MaterialComponentsModule {}
