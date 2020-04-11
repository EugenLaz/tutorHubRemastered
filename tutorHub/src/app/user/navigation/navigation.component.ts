import { Component, OnInit } from '@angular/core';
import {LoginService} from '../../service/login/login.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.scss']
})
export class NavigationComponent implements OnInit {

  constructor(private loginService: LoginService) { }

  ngOnInit(): void {
  }

  logout() {
    this.loginService.logout();
  }
}
