
import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import {VsInstanceInfo} from '../material-component/admin/vs-instance/vs-instance-info'
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class VsInstancesService {
  constructor(private http:HttpClient,private authService: AuthService){}

getVsInstancesData() {
  return this.http.get(environment.baseUrl+"vs/basic/vslcm/vsId", { withCredentials: true })
  .pipe(
    tap(_ => console.log('fetched Vs Instance - SUCCESS')),
    catchError(this.authService.handleError<any>('getVsInstancesData'))
    );
}

getVsInstanceByIdData(Id): Observable<any> {
  return this.http.get<any>(environment.baseUrl+"vs/basic/vslcm/vs/"+Id, { withCredentials: true })
  .pipe(
    tap(_ => console.log('fetched VsInstanceInfo - SUCCESS')),
    catchError(this.authService.handleError<any>('getVsInstanceByIdData'))
    );
}

deleteVsInstance(vsiId): Observable<String> {
  return this.http.delete(environment.baseUrl+"vs/basic/vslcm/vs/"+vsiId,{withCredentials: true})
  .pipe(
    tap((result: String) => this.authService.log(`deleted VS instance w/ id=${vsiId}`, 'SUCCESS', true)),
    catchError(this.authService.handleError<String>('deleteVsInstance'))
  );
}
terminateVsInstance(vsiId): Observable<String> {
  return this.http.post(environment.baseUrl+"vs/basic/vslcm/vs/"+vsiId+"/terminate", null, {withCredentials: true})
  .pipe(
    tap((result: String) => this.authService.log(`terminate VS instance w/ id=${vsiId}`, 'SUCCESS', true)),
    catchError(this.authService.handleError<String>('terminateVsInstance'))
  );
}
postVsInstanceData(vsInstRequest: Object): Observable<String> {
  return this.http.post(environment.baseUrl+"vs/basic/vslcm/vs/",vsInstRequest,{withCredentials: true})
  .pipe(
    tap((vsiId: String) => this.authService.log(`added VS instance w/ id=${vsiId}`, 'SUCCESS', true)),
    catchError(this.authService.handleError<String>('postVsInstanceData'))
  );
}

}


