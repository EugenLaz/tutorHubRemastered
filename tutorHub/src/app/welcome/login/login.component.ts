import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {LoginService} from '../../service/login/login.service';
import {ActivatedRoute, Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AppComponent} from '../../app.component';
import {ReedirectorService} from '../../service/login/reedirector.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  username: string;
  password: string;
  errorMessage = 'Invalid Credentials';
  successMessage: string;
  invalidLogin = false;
  loginSuccess = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private loginRedirector: ReedirectorService,
    private authenticationService: LoginService) {
  }
  handleLogin() {
    this.authenticationService.authenticationService(this.username, this.password).subscribe((result) => {
      this.invalidLogin = false;
      this.loginSuccess = true;
      this.successMessage = 'Login Successful.';
      this.loginRedirector.emitChildEvent(true);
      this.router.navigate(['/user/profile']);
    }, () => {
      this.invalidLogin = true;
      this.loginSuccess = false;
      this.loginRedirector.emitChildEvent(false);
    });
  }

}


