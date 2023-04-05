
import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import { NsliceInstancesInfo } from '../material-component/admin/n-slice-instances/n-slice-instances-info';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class NsliceInstancesService {
  constructor(private http:HttpClient,private router: Router,private authService: AuthService){}
  httpOptions = {
    headers: new HttpHeaders(
      { 'Content-Type': 'application/json'}),
      withCredentials: true,
      responseType: 'text' as 'json',
  };

  getNsliceInstancesData(): Observable<NsliceInstancesInfo[]> {
    return this.http.get<NsliceInstancesInfo[]>(environment.baseUrl+"vs​/basic​/nslcm​/ns", {withCredentials: true})
    .pipe(
      tap(_ => console.log('fetched network slice instance - SUCCESS')),
      catchError(this.authService.handleError<NsliceInstancesInfo[]>('getNsliceInstancesData', []))
      );
  
  }
  postNsliceInstancesData(NsliceInstancesRequest: Object): Observable<String> {
   
    return this.http.post(environment.baseUrl+"vs​/basic​/nslcm​/ns",NsliceInstancesRequest,this.httpOptions)
    .pipe(
      tap((nid: String) => this.authService.log(`added network slice instance w/ id=${nid}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postNsliceInstancesData'))
    );
  }
  postNsliceInstancesInstantiateData(NsliceInstancesInstantiateRequest: Object,nsiId): Observable<String> {
    return this.http.put(environment.baseUrl+"vs​/basic​/nslcm​/ns​/"+nsiId+"/action/instantiate",NsliceInstancesInstantiateRequest,{withCredentials: true})
    .pipe(
      tap((nsiId: String) => this.authService.log(`instantiated network slice instance w/ id=${nsiId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postNsliceInstancesInstantiateData'))
    );
  }
  deleteNsliceInstancesData(nsiUuid): Observable<String> {
    return this.http.put(environment.baseUrl+"vs​/basic​/nslcm​/ns​/"+nsiUuid+"/action/terminate",{withCredentials: true})
    .pipe(
      tap((result: String) => this.authService.log(`deleted network slice instance w/ id=${nsiUuid}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('deleteNsliceInstancesData'))
    );
      
  }
}



