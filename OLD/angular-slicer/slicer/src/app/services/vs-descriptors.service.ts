
import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import { VsDescriptorInfo } from '../material-component/admin/vs-descriptor/vs-descriptor-info';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class VsDescriptorsService {
  constructor(private http:HttpClient,private authService: AuthService){}


  getVsDescriptorsData(): Observable<VsDescriptorInfo[]> {
    return this.http.get<VsDescriptorInfo[]>(environment.baseUrl+"portal/catalogue/vsdescriptor", {withCredentials: true})
    .pipe(
      tap(_ => console.log('fetched VsDescriptorInfo - SUCCESS')),
      catchError(this.authService.handleError<VsDescriptorInfo[]>('getVsDescriptorsData', []))
      );
  }

  getVsDescriptorsByIdData(vsdId: string): Observable<any> {
    return this.http.get<any>(environment.baseUrl+"portal/catalogue/vsdescriptor/"+vsdId, {withCredentials: true})
      .pipe(
        tap(_ => console.log('fetched vsdescriptorInfoById - SUCCESS')),
        catchError(this.authService.handleError<any>('getVsDescriptorsByIdData'))
        );
  }

  postVsDescriptorsData(vsDescRequest: Object): Observable<String> {
    return this.http.post(environment.baseUrl+"portal/catalogue/vsdescriptor",vsDescRequest,{withCredentials: true})
    .pipe(
      tap((vsdId: String) => this.authService.log(`added VS Descriptor w/ id=${vsdId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postVsDescriptorsData'))
    );
  }

  deleteVsDescriptorsData(vsDescId: string): Observable<String> {
    return this.http.delete(environment.baseUrl+"portal/catalogue/vsdescriptor/"+vsDescId,{withCredentials: true})
    .pipe(
      tap((result: String) => this.authService.log(`deleted VS Descriptor w/ id=${vsDescId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('deleteVsDescriptorsData'))
    );
  }
}



