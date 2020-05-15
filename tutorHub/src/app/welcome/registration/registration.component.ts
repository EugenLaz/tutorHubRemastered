import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, NgModel, ValidationErrors, ValidatorFn, Validators} from '@angular/forms';
import {HttpClient, HttpParams} from '@angular/common/http';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.scss']
})
export class RegistrationComponent implements OnInit {
  model: MyUser = {
    username: '',
    email: '',
    password: '',
    repeatedPassword: '',
    role: ''
  };
  submited = false;
  loginForm: FormGroup;

  constructor(private route: ActivatedRoute,
              public formBuilder: FormBuilder,
              private http: HttpClient) {
    this.route.queryParams.subscribe(params => {
      this.model.role = params.userRole;
    });

    this.loginForm = this.formBuilder.group({
      username: new FormControl('', Validators.compose([
        Validators.required
      ])),
      email: new FormControl('', Validators.compose([
        Validators.required,
        Validators.email
      ])),
      password: new FormControl('', Validators.compose([
        Validators.required
      ])),
      confirmPassword: new FormControl('', Validators.compose([
        Validators.required
      ])),
    }, {
      validators: passwordsMatchValidator
    });
  }

  ngOnInit() {
  }

  submit() {
    const url = 'http://localhost:8080/register';
    if (this.loginForm.valid) {
      this.http.post<string>(url, JSON.stringify(this.model)).subscribe(
        res => {
          alert(res);
        });
    }
  }

}

export interface MyUser {
  username: string;
  email: string;
  password: string;
  repeatedPassword: string;
  role: string;
}

export const passwordsMatchValidator: ValidatorFn = (control: FormGroup): ValidationErrors | null => {
  const confirmPassword = control.get('confirmPassword');
  const password = control.get('password');
  return password && confirmPassword && password.value === confirmPassword.value ? null : {confirmNotMatches: true};
};
