
import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { HttpClient} from '@angular/common/http';
import {DomainsInfo} from '../material-component/domains/domains-info';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class DomainsService {
  constructor(private http:HttpClient,private authService: AuthService){}

  getDomainsData(): Observable<DomainsInfo[]> {
  return this.http.get<DomainsInfo[]>(environment.baseUrl+"domainLayer/catalogue", {withCredentials: true})
  .pipe(
    tap(_ => console.log('fetched domain - SUCCESS')),
    catchError(this.authService.handleError<DomainsInfo[]>('getDomainsData', []))

    );
}
  getDomainsDetailsData(name): Observable<DomainsInfo[]> {
    return this.http.get<DomainsInfo[]>(environment.baseUrl+"domainLayer/catalogue/"+name,{withCredentials: true})
    .pipe(
      tap(_ => console.log('fetched domain info - SUCCESS')),
      catchError(this.authService.handleError<DomainsInfo[]>('getDomainsDetailsData', []))

      );
  }

  postDomain(domainRequest: Object): Observable<String> {

    return this.http.post(environment.baseUrl+"domainLayer/catalogue",domainRequest,{withCredentials: true})
    .pipe(
      tap((domainId: String) => this.authService.log(`added domain w/ id=${domainId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('postDomain'))
    );
  }

  deleteDomain(domainId): Observable<String> {
    return this.http.delete(environment.baseUrl+"domainLayer/catalogue/"+domainId,{withCredentials: true})
    .pipe(
      tap((result: String) => this.authService.log(`deleted domain w/ id=${domainId}`, 'SUCCESS', true)),
      catchError(this.authService.handleError<String>('deleteDomain'))
    );
  }
}




