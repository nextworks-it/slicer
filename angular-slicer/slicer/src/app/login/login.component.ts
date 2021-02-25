import { LocationStrategy } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { TokenStorageService } from '../services/token-storage.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginFormGroup: FormGroup;  
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private router: Router,
    private authService: AuthService, 
    private _formBuilder: FormBuilder,
    ) { }

  ngOnInit() {
    /*
    if (this.tokenStorage.getToken()) {
      this.isLoggedIn = true;
      this.roles = this.tokenStorage.getUser().roles;
    }
    */
    this.loginFormGroup = this._formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  parse_link_header(header) {
    if (header.length == 0) {
      return ;
    }

    let parts = header.split(',');
    var links = {};
    parts.forEach( p => {
      let section = p.split(';');
      var url = section[0].replace(/<(.*)>/, '$1').trim();
      var name = section[1].replace(/rel="(.*)"/, '$1').trim();
      links[name] = url;

    });
    return links;
  }  
/*
  onSubmit() {
    var loginInfo = {};
    var username = this.loginFormGroup.get('username').value;
    var password = this.loginFormGroup.get('password').value;
    if (username && password) {
      loginInfo['username'] = username;
      loginInfo['password'] = password;

    this.authService.login(loginInfo, '/dashboard').subscribe(res: HttpResponse<any> => {
        
    });
  }

}
*/
onSubmit() {
  var loginInfo = {};
  var username = this.loginFormGroup.get('username').value;
  var password = this.loginFormGroup.get('password').value;
  if (username && password) {
    loginInfo['username'] = username;
    loginInfo['password'] = password;
    
  this.authService.login(loginInfo).subscribe(
    (res: HttpResponse<any>) => {
      console.log('response from server:', res);
      console.log('response headers',res.headers.keys());
      console.log('JWT:', res.headers.get('Set-Cookie'));
      /*
      this.router.navigate(['dashboard']).then(() => {
        window.location.reload();
      }).catch(console.error);
      */
    }
  );
  

}

}


  reloadPage() {
    window.location.reload();
  }
}
