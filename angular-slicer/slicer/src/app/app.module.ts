import { AdminComponent } from './material-component/admin/admin.component';
import { GroupComponent } from './material-component/admin/group/group.component';
//import { StorageServiceModule } from 'ngx-webstorage-service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';
import { LocationStrategy, PathLocationStrategy, HashLocationStrategy } from '@angular/common';
import { AppRoutes } from './app.routing';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
//import { AuthInterceptor } from './auth.interceptor';

import { FlexLayoutModule } from '@angular/flex-layout';
import { FullComponent } from './layouts/full/full.component';
import { AppHeaderComponent } from './layouts/full/header/header.component';
import { AppSidebarComponent } from './layouts/full/sidebar/sidebar.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DemoMaterialModule } from './demo-material-module';
import { SharedModule } from './shared/shared.module';
import { SpinnerComponent } from './shared/spinner.component';
import { LoginComponent } from './login/login.component';
//import { CookieService } from 'ngx-cookie-service';

//import { authInterceptorProviders } from './_helpers/auth.interceptor';

@NgModule({
  declarations: [
    AppComponent,
    FullComponent,
    AppHeaderComponent,
    SpinnerComponent,
    AppSidebarComponent,
    GroupComponent,
    LoginComponent,
    //StorageServiceModule
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    DemoMaterialModule,
    FormsModule,
    FlexLayoutModule,
    HttpClientModule,
    SharedModule,
    ReactiveFormsModule,
    RouterModule.forRoot(AppRoutes, { useHash: true })
  ],
  providers: [
    //authInterceptorProviders
    /*{
      provide: CookieService,
      useClass: PathLocationStrategy
    }*/
    {provide: LocationStrategy, useClass: HashLocationStrategy}
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}
