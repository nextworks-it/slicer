import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-second-login',
  templateUrl: './second-login.component.html',
  styleUrls: ['./second-login.component.css']
})
export class SecondLoginComponent implements OnInit {
  loginFormGroup: FormGroup;  
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];
  groupName:string;
  constructor(private router: Router,
    private authService: AuthService, 
    private _formBuilder: FormBuilder,
    ) { }

  ngOnInit() {
    this.loginFormGroup = this._formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }
  

onSubmit() {
  var loginInfo = {};
  var username = this.loginFormGroup.get('username').value;
  var password = this.loginFormGroup.get('password').value;
  if (username && password) {
    loginInfo['username'] = username;
    loginInfo['password'] = password;
    localStorage.setItem('username',username);
  this.authService.login(loginInfo).subscribe(
    (res: HttpResponse<any>) => {
      this.authService.checkUser().subscribe(res=> {
        this.groupName=res.role;
        
        localStorage.setItem('group',this.groupName);
        this.router.navigate(['dashboard']).then(() => {
          window.location.reload();
 }).catch(console.error);        
    }  
      );
      
    }

  );
}

}


  reloadPage() {
    window.location.reload();
  }
}
