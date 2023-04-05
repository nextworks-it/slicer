

//import { LoginComponent } from './login/login.component';
import { SecondLoginComponent } from './material-component/second-login/second-login.component';

import { Routes } from '@angular/router';
import {DashboardComponent} from './dashboard/dashboard.component'
import { FullComponent } from './layouts/full/full.component';
export const AppRoutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full'
      },
      {
        path: '',
        loadChildren:
          () => import('./material-component/material.module').then(m => m.MaterialComponentsModule)
      },
      {
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
      },
      {
        path: 'dashboard',
      component : DashboardComponent,
          
    },

 
    ]
  },
  
];
/*
@RouteConfig([
  { path: '/addDisplay', component: AddDisplay, as: 'addDisplay' },
  { path: '/<secondComponent>', component: '<secondComponentName>', as: 'secondComponentAs' },
])
*/

/*
import { Routes } from '@angular/router';

import { FullComponent } from './layouts/full/full.component';

export const AppRoutes: Routes = [
  {
    path: '',
    component: FullComponent,
    children: [
      {
        path: '',
        redirectTo: '/dashboard',
        pathMatch: 'full'
      },
      {
        path: '',
        loadChildren:
          () => import('./material-component/material.module').then(m => m.MaterialComponentsModule)
      },
      {
        path: 'login',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule)
      }
    ]
  }
];
*/