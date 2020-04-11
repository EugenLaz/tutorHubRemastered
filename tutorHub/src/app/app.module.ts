import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {Routes, Router, RouterModule} from '@angular/router';
import { AppRoutingModule } from './app-routing.module';


import { AppComponent } from './app.component';
import { WelcomeComponent } from './welcome/welcome.component';
import { LoginComponent } from './welcome/login/login.component';
import { RegistrationComponent } from './welcome/registration/registration.component';
import { StartComponent } from './welcome/start/start.component';
import {AlertModule} from 'ngx-bootstrap';
import { HttpInterceptorServiceService } from './service/login/http-interceptor-service.service';
import { UserComponent } from './user/user.component';
import { ProfileComponent } from './user/profile/profile.component';
import { NavigationComponent } from './user/navigation/navigation.component';

const appRouts: Routes = [
  {path: 'login',
  component: LoginComponent},
  {path: 'register',
  component: RegistrationComponent},
  {path: '',
  component: StartComponent
  },
];



@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    LoginComponent,
    RegistrationComponent,
    StartComponent,
    UserComponent,
    ProfileComponent,
    NavigationComponent,
  ],
  imports: [
    RouterModule.forChild(appRouts),
    FormsModule,
    MatSidenavModule,
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AlertModule,
    ReactiveFormsModule,
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpInterceptorServiceService,
    multi: true
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}

