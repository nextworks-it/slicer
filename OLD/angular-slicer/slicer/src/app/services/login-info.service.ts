import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { GroupInfo } from '../material-component/admin/group/group-info';
import { AuthService } from './auth.service';
import { HttpClient,HttpHeaders,HttpParams } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class LoginInfoService {
  constructor(private http:HttpClient,private authService: AuthService){}



getUserInfo(): Observable<GroupInfo[]> {
  return this.http.get<GroupInfo[]>(environment.baseUrl+"vs/whoami", { withCredentials: true })
  .pipe(
    tap(_ => console.log('fetched GroupInfo - SUCCESS')),
    catchError(this.authService.handleError<GroupInfo[]>('getUserInfo', []))
    );
}

}


