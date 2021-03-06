import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  // BASE_PATH: 'http://localhost:8080'
  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUserName';
  USER_ROLE_SESSION_ATTRIBUTE_NAME = 'authenticatedUserRole';
  public username: string;
  public password: string;
  private childClickedEvent = new BehaviorSubject<boolean>(false);

  constructor(private http: HttpClient) {
  }

  emitChildEvent(sucess: boolean) {
    this.childClickedEvent.next(sucess);
  }

  childEventListner() {
    return this.childClickedEvent.asObservable();
  }

  authenticationService(username: string, password: string) {
    return this.http.get(`http://localhost:8080/basicauth`,
      {headers: {authorization: this.createBasicAuthToken(username, password)}})
      .pipe(map((res) => {
        this.username = username;
        this.password = password;
        this.registerSuccessfulLogin(username);
        this.registerSuccessfulRole(username);
      }));
  }

  createBasicAuthToken(username: string, password: string) {
    return 'Basic ' + window.btoa(username + ':' + password);
  }

  registerSuccessfulLogin(username) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, username);
  }

  registerSuccessfulRole(username) {
    this.http.get<string>('http://localhost:8080/getRole?username=' + username)
      .subscribe(res => sessionStorage.setItem(this.USER_ROLE_SESSION_ATTRIBUTE_NAME, res));
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
    if (user === null) {
      return false;
    }
    return true;
  }

  getLoggedInUserName() {
    const user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return '';
    }
    return user;
  }

  getLoggedInUserRole() {
    const user = sessionStorage.getItem(this.USER_ROLE_SESSION_ATTRIBUTE_NAME);
    if (user === null) {
      return '';
    }
    return user;
  }
}

