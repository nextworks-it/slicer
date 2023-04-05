/*
import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpResponse, HttpParams} from '@angular/common/http';
import { Observable,of } from 'rxjs';
import { environment } from '../environments/environments';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from './message.service';
import { catchError, tap } from 'rxjs/operators';
import { utils } from './utils';
import 'url-search-params-polyfill';

import { Router} from '@angular/router';

const AUTH_API = environment.baseUrl;

const httpOptions = {
  headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
  withCredentials: true,
  observe: 'response' as 'response'
};

export class Token {
  access_token: string;
  refresh_token: string;
  username: string;
}

export class Role {
  id: string;
  name: string;
  clientRole: boolean;
  composite: boolean;
  containerId: string;
  description: string;
}

export class RoleDetails {
  details: Role[];
}


export class UseCases{
  useCases: string[];
}

export class RegistrationDetails {
  details: {
    user_id: string;
  }
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private router: Router,
    private http: HttpClient,
    private _snackBar: MatSnackBar,
    private messageService: MessageService,
   // private utl: utils,
    ) { 
  }


checkUser(): Observable<any> {
  return this.http.get<any>(AUTH_API + 'vs/whoami', {withCredentials: true})
}
login(loginInfo: any): Observable<any> {
  localStorage.setItem('username',loginInfo['username']);
  localStorage.setItem('logged', 'true');
  var loginInfos = 'username=' + loginInfo['username'] + '&password=' + loginInfo['password'];
  return this.http.post(AUTH_API + 'login', loginInfos, httpOptions)
}

logout(){
  localStorage.clear();
  this.router.navigateByUrl('login').then(() => {
    window.location.reload();
}
  )
}

refresh(refreshInfo: Object) {
  
  return this.http.post(AUTH_API + 'refreshsession', refreshInfo, httpOptions)
    .pipe(
      tap((_) =>
      {
        //this.utl.deleteCookie(localStorage.getItem("username"))
        this.checkUser().subscribe(res=> {
          localStorage.setItem('group',res.role);
          this.router.navigate(['dashboard']).then(() => {
            window.location.reload();
       }).catch(console.error);        
          
          
        this.log(`refresh login`, 'SUCCESS', false);
      })
      })
    )
}

handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    if (error.status == 401) {
      if (operation.indexOf('refresh') >= 0 || operation.indexOf('login') >= 0) {
        // TODO: better job of transforming error for user consumption
        this.log(`${operation} failed: ${error.message}`, 'FAILED', false);
        localStorage.clear();
        this.router.navigate(['/login']).then(() => {
          window.location.reload();
        });
      } else {
        this.log(`${operation} failed: ${error.message}`, 'FAILED', false);
        this.refresh({
          access_token: localStorage.getItem('token'),
          refresh_token: localStorage.getItem('refreshtoken')
        }).subscribe(token => {

        });
      }

    } else {
      if (error.status == 400) {
        if (operation.indexOf('refresh') >= 0 || operation.indexOf('login') >= 0) {
          // TODO: better job of transforming error for user consumption
          this.log(`${operation} failed: ${error.message}`, 'FAILED', false);
          localStorage.clear();
          this.router.navigate(['/login']).then(() => {
            window.location.reload();
          });
        }
      } else {
        console.log(error.status + " after " + operation);
        this.log("Account not yet activated", 'FAILED', true);
      }
    }

    // Let the app keep running by returning an empty result.
    return of(result as T);
  };
}

log(message: string, action: string, reload: boolean) {
  this.messageService.add(`${message}`);
  this.openSnackBar(`${message}`, action, reload);
}

openSnackBar(message: string, action: string, reload: boolean) {
  this._snackBar.open(message, action, {
    duration: 0,
  }).afterDismissed().subscribe(() => {
    //console.log('The snack-bar was dismissed');
   // if (reload)
    //  window.location.reload();
  });
}

}
*/

import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders, HttpResponse, HttpParams} from '@angular/common/http';
import { Observable,of } from 'rxjs';
import { environment } from '../environments/environments';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageService } from './message.service';
import { catchError, tap } from 'rxjs/operators';
import 'url-search-params-polyfill';

import { Router} from '@angular/router';

const AUTH_API = environment.baseUrl;

const httpOptions = {
  headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded'),
  withCredentials: true,
  observe: 'response' as 'response'
};
const httpOptionsS = {
 //headers: new HttpHeaders().set('Access-Control-Allow-Origin','*'),
  withCredentials: true,
};
@Injectable({
  providedIn: 'root'
})
export class AuthService {


  constructor(private router: Router,
    private http: HttpClient,
    private _snackBar: MatSnackBar,
    private messageService: MessageService,
    ) { 
  }


checkUser(): Observable<any> {
  return this.http.get<any>(AUTH_API + 'vs/whoami', httpOptionsS)
}

login(loginInfo: any): Observable<any> {
  localStorage.setItem('username',loginInfo['username']);
  localStorage.setItem('logged', 'true');
  //this.setCookie('username', loginInfo['username'], 1);
  var loginInfos = 'username=' + loginInfo['username'] + '&password=' + loginInfo['password'];
  return this.http.post(AUTH_API + 'login', loginInfos, httpOptions)  
  .pipe(
    tap((_) => this.log(`login is`, 'SUCCESS', true)),
    catchError(this.handleError<String>('login'))
  );
  
}

logout(){
  this.deleteCookie('username');
  localStorage.clear();
  this.router.navigateByUrl('login').then(() => {
    window.location.reload();
}
  )
}

 getCookie(cname) {
  var name = cname + '=';
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
  return '';
}

 deleteCookie(cname) {
  var cvalue = this.getCookie(cname);
  var d = new Date();
  d.setTime(d.getTime() - 3 * 24 * 60 * 60 * 1000);
  var expires = 'expires=' + d.toUTCString();
  document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
}


 setCookie(cname, cvalue, exdays) {
  var d = new Date();
  d.setTime(d.getTime() + exdays * 24 * 60 * 60 * 1000);
  var expires = 'expires=' + d.toUTCString();
  document.cookie = cname + '=' + cvalue + ';' + expires + ';path=/';
}

refresh(refreshInfo: Object) {
  return this.http.post(AUTH_API + 'refreshsession', refreshInfo, httpOptions)
    .pipe(
      tap((_) =>
      {
        this.deleteCookie('username');      
        this.checkUser().subscribe(data=> {
          this.setCookie('username', data.username, 1);
          this.router.navigate(['dashboard']).then(() => {
            window.location.reload();
       }).catch(console.error);        
        this.log(`refresh login`, 'SUCCESS', false);
      })
      })
    )
}

handleError<T> (operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    if (error.status == 401) {
      if (operation.indexOf('refresh') >= 0 || operation.indexOf('login') >= 0) {
        this.log(`${operation} failed: ${error.message}`, 'FAILED', false);
        localStorage.clear();
        this.router.navigate(['/login']).then(() => {
          //window.location.reload();
        });
      } else {
        this.log(`${operation} failed: ${error.message}`, 'FAILED', false);
      }

    } 
    else if (error.status == 201) {
      console.log("status code 201");

    } 
    else if( error.status == 409){
      this.log("Duplicate : continue or add new one", 'CONTINUE', true);
    } 
    else if( error.status == 404){
      console.log("Not Found")
    }     
    else if( error.status == 500){
      this.log(error.error, 'FAILED', false);
    } 
    else {
      if (error.status == 400) {
        if (operation.indexOf('refresh') >= 0 || operation.indexOf('login') >= 0) {
          this.log(`${operation} failed: ${error.message}`, 'FAILED', false);
          localStorage.clear();
          this.router.navigate(['/login']).then(() => {
            window.location.reload();
          });
        }
      } else {
        console.log(error.status + " after " + operation);
       // this.log("Account not yet activated", 'FAILED', true);
      }
    }
    return of(result as T);
  };
}

log(message: string, action: string, reload: boolean) {
  this.messageService.add(`${message}`);
  this.openSnackBar(`${message}`, action, reload);
}

openSnackBar(message: string, action: string, reload: boolean) {
  this._snackBar.open(message, action, {
    duration: 0,
  }).afterDismissed().subscribe(() => {
    //console.log('The snack-bar was dismissed');
  });
}

}
