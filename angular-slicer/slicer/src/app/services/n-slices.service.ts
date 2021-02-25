
import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import { NslicesInfo } from '../material-component/admin/n-slices/n-slices-info';
import { Router } from '@angular/router';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class NslicesService {
  constructor(private http:HttpClient,private router: Router,private authService: AuthService){}
  httpOptions = {
    headers: new HttpHeaders(
      { 'Content-Type': 'application/json'}),
      withCredentials: true
  };

  getNslicesData(): Observable<NslicesInfo[]> {
    return this.http.get<NslicesInfo[]>(environment.baseUrl+"ns/catalogue/nstemplate", {withCredentials: true})
    .pipe(
      tap(_ => console.log('fetched network slice template - SUCCESS')),
      catchError(this.authService.handleError<NslicesInfo[]>('getNslicesData', []))
      );
  }
  postNslicesData(NslicesRequest: Object): Observable<String> {
   
    return this.http.post(environment.baseUrl+"ns/catalogue/nstemplate",NslicesRequest,{withCredentials: true})
    .pipe(
      tap((nstId: String) => this.authService.log(`added network slice template w/ id=${nstId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postNslicesData'))
    );
  }

  deleteNslicesData(nstId): Observable<String> {
    return this.http.delete(environment.baseUrl+"ns/catalogue/nstemplate/"+nstId,{withCredentials: true})
    /* 
    .pipe(
        tap((result: String)=> this.router.navigate(['nslices']))
        
        //catchError(this.authService.handleError<GroupInfo>('getVsBlueprint'))
      );
      */
     .pipe(
    //  tap((result: String) => this.authService.log(`deleted network slice template w/ id=${nstId}`, 'SUCCESS', true)),
      tap((result: String) => console.log(`deleted network slice template w/ id=${nstId}`)),
      catchError(this.authService.handleError<String>('deleteNslicesData'))
    );
      
  }
}



