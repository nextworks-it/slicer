import { Component, OnInit } from '@angular/core';
import {CookieService} from 'ngx-cookie-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  private cookieValue : string;
  constructor(private cookieService : CookieService){
  }
  ngOnInit(){
    /*
    this.cookieService.set("cookie-name","cookie-value");
    this.cookieValue=this.cookieService.get("cookie-name")
    */
  }
}
