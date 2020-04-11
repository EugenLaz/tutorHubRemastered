import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import { map } from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  // BASE_PATH: 'http://localhost:8080'
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser';
  private childClickedEvent = new BehaviorSubject<boolean>(false);

  public username: string;
  public password: string;



  emitChildEvent(sucess: boolean) {
    this.childClickedEvent.next(sucess);
  }

  childEventListner() {
    return this.childClickedEvent.asObservable();
  }
  constructor(private http: HttpClient) {
  }

  authenticationService(username: string, password: string) {
    return this.http.get(`http://localhost:8080/basicauth`,
      { headers: { authorization: this.createBasicAuthToken(username, password) } }).pipe(map((res) => {
      this.username = username;
      this.password = password;
      this.registerSuccessfulLogin(username, password);
    }));
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

  registerSuccessfulLogin(username, password) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.username = null;
    this.password = null;
    this.http.post('http://localhost:8080/logout', {}).subscribe(res => {
      console.log('res');
    });
  }

  isUserLoggedIn() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) { return false; }
    return true;
  }

  getLoggedInUserName() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) { return ''; }
    return user;
  }
}

