import { Component } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: []
})
export class AppHeaderComponent {

  constructor(private router: Router,
    private authService: AuthService, 
    ) { }

  signOut() {
    this.authService.logout()
    /*.subscribe(
      (res: HttpResponse<any>) => {
        console.log('response from server:', res);
        console.log('response headers',res.headers.keys());
        console.log('JWT:', res.headers.get('Set-Cookie'));
        this.router.navigate(['dashboard']).then(() => {
          window.location.reload();
        }).catch(console.error);
      }
    );
    */
  
  
  
  }
}
