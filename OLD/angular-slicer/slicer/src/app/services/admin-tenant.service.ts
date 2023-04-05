import { environment } from '../environments/environments';
import { Injectable } from '@angular/core';
import { Observable, } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { AdminTenantInfo } from '../material-component/admin/admintenant/admin-tenant-info';
import { HttpClient } from '@angular/common/http';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})

export class AdminTenantService {
  constructor(private http:HttpClient,private authService: AuthService){}



getTenantData(groupId): Observable<AdminTenantInfo[]> {
  return this.http.get<AdminTenantInfo[]>(environment.baseUrl+"vs/admin/group/"+groupId, {withCredentials: true})
  .pipe(
    tap(_ => console.log('fetched tenant - SUCCESS')),
    catchError(this.authService.handleError<AdminTenantInfo[]>('getTenantData', []))

    );
}

}


