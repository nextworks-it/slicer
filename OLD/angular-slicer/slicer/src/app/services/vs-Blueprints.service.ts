
import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';
import {VsBlueprintInfo} from '../material-component/admin/vs-blueprint/vs-blueprint-info';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class VsBlueprintsService {
  constructor(private http:HttpClient,private authService: AuthService){}

  getVsBlueprint(vsBlueprintId: string): Observable<VsBlueprintInfo> {
    return this.http.get<VsBlueprintInfo>(environment.baseUrl+"portal/catalogue/vsblueprint/"+vsBlueprintId, {withCredentials: true})
      .pipe(
        tap(_ => console.log('fetched vsBlueprintInfo - SUCCESS')),
        catchError(this.authService.handleError<VsBlueprintInfo>('getVsBlueprint'))
        );
  }

  getVsBlueprintsData(): Observable<VsBlueprintInfo[]> {
  return this.http.get<VsBlueprintInfo[]>(environment.baseUrl+"portal/catalogue/vsblueprint", {withCredentials: true})
  .pipe(
    tap(_ => console.log('fetched vsBlueprintInfos - SUCCESS')),
    catchError(this.authService.handleError<VsBlueprintInfo[]>('getVsBlueprintsData', []))
    );

}
  postVsBlueprintsData(vsbRequest: Object): Observable<String> {
    
  return this.http.post(environment.baseUrl+"portal/catalogue/vsblueprint",vsbRequest,{withCredentials: true})
  .pipe(
    tap((blueprintId: String) => this.authService.log(`added VS Blueprint w/ id=${blueprintId}`, 'SUCCESS', true)),
    catchError(this.authService.handleError<String>('postVsBlueprintsData'))
  );
}
deleteVsBlueprintsData(vsBlueprintId: string): Observable<String> {
  return this.http.delete(environment.baseUrl+"portal/catalogue/vsblueprint/"+vsBlueprintId,{withCredentials: true})
  .pipe(
   // tap((result: String) => this.authService.log(`deleted VS Blueprint w/ id=${vsBlueprintId}`, 'SUCCESS', true)),
    tap((result: String) => console.log(`deleted VS Blueprint w/ id=${vsBlueprintId}`)),
    catchError(this.authService.handleError<String>('deleteVsBlueprintsData'))
  );
}


}


