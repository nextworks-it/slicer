import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { GroupInfo } from '../material-component/admin/group/group-info';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class GroupService {
  constructor(private http:HttpClient,private authService: AuthService){}

postGroup(groupRequest: Object): Observable<String> {

    return this.http.post(environment.baseUrl+"vs/admin/group/"+groupRequest,groupRequest,{withCredentials: true})
    .pipe(
      tap((groupId: String) => this.authService.log(`added group w/ id=${groupId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postGroup'))
    );
  }

getGroupData(): Observable<GroupInfo[]> {
  return this.http.get<GroupInfo[]>(environment.baseUrl+"vs/admin/group/", {withCredentials: true})
  .pipe(
    tap(_ => console.log('fetched GroupInfo - SUCCESS')),
    catchError(this.authService.handleError<GroupInfo[]>('getGroupData', []))
    );
}
deleteGroup(groupId: string): Observable<String> {
  return this.http.delete(environment.baseUrl+"vs/admin/group/"+groupId,{withCredentials: true})
  .pipe(
    tap((result: String) => this.authService.log(`deleted group w/ id=${groupId}`, 'SUCCESS', true)),
    catchError(this.authService.handleError<String>('deleteGroup'))
  );
}
}


