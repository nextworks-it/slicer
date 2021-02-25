import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { SlaInfo } from '../material-component/admin/sla/sla-info';
import { HttpClient, HttpHeaders, HttpParams, HttpRequest } from '@angular/common/http';
import { AuthService } from './auth.service';
import { AdminTenantInfo } from '../material-component/admin/admintenant/admin-tenant-info';

@Injectable({
  providedIn: 'root'
})

export class TenantService {
  constructor(private http:HttpClient,private authService: AuthService){}


postTenantData(tenantRequest: Object,groupId): Observable<String> {
    return this.http.post(environment.baseUrl+"vs/admin/group/"+ groupId+ '/tenant',tenantRequest,{withCredentials: true})
    .pipe(
      tap((tenantId: String) => this.authService.log(`added tenant w/ id=${tenantId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postTenantData'))
    );
  }
  

getTenantData(groupId): Observable<AdminTenantInfo[]> {
  return this.http.get<AdminTenantInfo[]>(environment.baseUrl+"vs/admin/group/"+groupId, {withCredentials: true})
  .pipe(
    tap(_ => console.log('fetched tenant - SUCCESS')),
    catchError(this.authService.handleError<AdminTenantInfo[]>('getTenantData', []))
    );
}
postSlaData(slatRequest: Object,groupId,userId): Observable<String> {
      return this.http.post(environment.baseUrl+"vs/admin/group/"+groupId+"/tenant/"+userId+'/sla',slatRequest,{withCredentials: true})
      .pipe(
        tap((slaId: String) => this.authService.log(`added sla w/ id=${slaId}`, 'SUCCESS', true)),
        catchError(this.authService.handleError<String>('postSlaData'))
      );
    }
getTenantSLAsData(groupId,userId): Observable<SlaInfo[]> {
      return this.http.get<SlaInfo[]>(environment.baseUrl+"vs/admin/group/"+ groupId + "/tenant/" + userId + '/sla',{withCredentials: true})
      .pipe(
        tap(_ => console.log('fetched sla - SUCCESS')),
        catchError(this.authService.handleError<SlaInfo[]>('getTenantSLAsData', []))
        );
    }
deleteSLAs(groupId,userId,slaId): Observable<String> {
      return this.http.delete(environment.baseUrl+"vs/admin/group/"+ groupId + "/tenant/" + userId + '/sla/'+slaId,{withCredentials: true})
      .pipe(
        tap((result: String) => this.authService.log(`deleted sla w/ id=${slaId}`, 'SUCCESS', true)),
        catchError(this.authService.handleError<String>('deleteSLAs'))
      );
    }

deleteTenant(groupId,userId): Observable<String> {
      return this.http.delete(environment.baseUrl+"vs/admin/group/"+groupId+ '/tenant/' + userId,{withCredentials: true})
      .pipe(
        tap((result: String) => this.authService.log(`deleted tenant w/ id=${userId}`, 'SUCCESS', true)),
        catchError(this.authService.handleError<String>('deleteTenant'))
      );
    }
  }


