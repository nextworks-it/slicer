import { SecondLoginComponent } from './second-login/second-login.component';
import { FullComponent } from './../layouts/full/full.component';
import { DomainsDetailsComponent } from './domains-details/domains-details.component';
import { DomainsComponent } from './domains/domains.component';
import { VsBlueprintComponent } from './admin/vs-blueprint/vs-blueprint.component';
import { NSlicesComponent } from './admin/n-slices/n-slices.component';
import { VsInstanceComponent } from './admin/vs-instance/vs-instance.component';
import { VsDescriptorComponent } from './admin/vs-descriptor/vs-descriptor.component';
import { VsDescriptorStepperComponent } from './admin/vs-descriptor-stepper/vs-descriptor-stepper.component';
import {VsBlueprintStepperComponent} from './admin/vs-blueprint-stepper/vs-blueprint-stepper.component'
import { GroupComponent } from './admin/group/group.component';
import { Routes } from '@angular/router';
import { AdminTenantComponent } from './admin/admintenant/admin-tenant.component';
import {LoginComponent} from '../login/login.component';
import {DashboardComponent} from '../dashboard/dashboard.component';
import { SlaComponent } from './admin/sla/sla.component';
import { DomainsWizardComponent } from './admin/domains-wizard/domains-wizard.component';
import { NSlicesStepperComponent } from './n-slices-stepper/n-slices-stepper.component';
import {VsInstanceDetailsComponent} from './vs-instance-details/vs-instance-details.component'
import {VsInstanceStepperComponent} from './admin/vs-instance-stepper/vs-instance-stepper.component'
import {VsBlueprintDetailsComponent} from './admin/vs-blueprint-details/vs-blueprint-details.component'
import {NSliceInstancesComponent} from './admin/n-slice-instances/n-slice-instances.component'
import {NSliceInstancesStepperComponent} from './admin/n-slice-instances-stepper/n-slice-instances-stepper.component'

export const MaterialRoutes: Routes = [
  {
    path: 'login',
    component: SecondLoginComponent
  },
  {
    path: 'dashboard',
    component: DashboardComponent
  },
  {
    path: 'tenant',
    component: AdminTenantComponent
  },
  {
    path: 'vsdescriptor',
    component: VsDescriptorComponent
  },
  {
    path: 'vsdescriptor-add',
    component: VsDescriptorStepperComponent
  },
  {
    path: 'vsinstance',
    component: VsInstanceComponent
  },
  {
    path: 'vsinstance-add',
    component: VsInstanceStepperComponent
  },
  {
    path: 'nslices',
    component: NSlicesComponent
  }, 
  {
    path: 'vsblueprint',
    component: VsBlueprintComponent
  },
  {
    path: 'onboarding',
    component: VsBlueprintStepperComponent
  },
  {
    path: 'group',
    component: GroupComponent
  },
  {
    path: 'domains',
    component: DomainsComponent
  },
  {
    path: 'domains-add',
    component: DomainsWizardComponent
  },
  {
    path: 'nslices-add',
    component: NSlicesStepperComponent
  },
  
 {
  path: 'sla',
  component: SlaComponent
},  
{
  path: 'domains-details',
  component: DomainsDetailsComponent
},
{
  path: 'vsi-details',
  component: VsInstanceDetailsComponent
},
{
  path: 'blueprints-vs-details',
  component: VsBlueprintDetailsComponent
},
{
  path: 'ns-instances',
  component: NSliceInstancesComponent
},
{
  path: 'ns-instances-add',
  component: NSliceInstancesStepperComponent
},

{
  path: 'full',
  component: FullComponent

},
{ path: '', redirectTo: '/login', pathMatch: 'full' }, // redirect to `first-component`
];
