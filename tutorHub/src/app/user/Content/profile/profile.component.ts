import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {LoginService} from '../../../service/login/login.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  userInfo: UserInfo = {
    name: '',
    email: '',
    personalInfo: '',
    balance: 0,
    rating: 0
  };

  constructor(private http: HttpClient, private loginService: LoginService) {
  }

  ngOnInit(): void {
    this.getUserData();
  }

  public getUserData() {
    const url = 'http://localhost:8080/getUserInfo';
    const username = this.loginService.getLoggedInUserName();
    const params = new HttpParams().set('username', username);
    this.http.get<UserInfo>(url, {params}).subscribe(res => {
      this.userInfo = res;
    });
  }

  public updateUserInfo() {
    const url = 'http://localhost:8080/updateProfile';
    this.http.post<string>(url, JSON.stringify(this.userInfo)).subscribe(
      res => {
        alert(res);
      });
  }

  public getRating(): string {
    let result: string;
    if (this.userInfo.rating === 0) {
      result = 'Is not ready yet';
    } else {
      result = this.userInfo.rating.toString();
    }
    return result;
  }
}

export interface UserInfo {
  name: string;
  email: string;
  personalInfo: string;
  balance: number;
  rating: number;
}
