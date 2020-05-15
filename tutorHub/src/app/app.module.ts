import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';
import {Routes, Router, RouterModule} from '@angular/router';
import {AppRoutingModule} from './app-routing.module';


import {AppComponent} from './app.component';
import {WelcomeComponent} from './welcome/welcome.component';
import {LoginComponent} from './welcome/login/login.component';
import {RegistrationComponent} from './welcome/registration/registration.component';
import {StartComponent} from './welcome/start/start.component';
import {AlertModule} from 'ngx-bootstrap';
import {HttpInterceptorServiceService} from './service/login/http-interceptor-service.service';
import {UserComponent} from './user/user.component';
import {ProfileComponent} from './user/Content/profile/profile.component';
import {NavigationComponent} from './user/navigation/navigation.component';
import {TutorsBrowseComponent} from './user/Content/tutors-browse/tutors-browse.component';
import {RoutProtectionService} from './service/rout-protection/rout-protection.service';
import {NgxPaginationModule} from 'ngx-pagination';
import {LessonRequestComponent} from './user/Content/lesson-request/lesson-request.component';
import {MyRequestsComponent} from './user/Content/my-requests/my-requests.component';
import {MyLessonsComponent} from './user/Content/my-lessons/my-lessons.component';
import {TutorSearchPipe} from './service/filter-pipes/tutor-search.pipe';
import {CalendarModule, DateAdapter} from 'angular-calendar';
import {adapterFactory} from 'angular-calendar/date-adapters/date-fns';
import {PaymentComponent} from './user/Content/payment/payment.component';
import {MapComponent} from './user/Content/lesson-request/map/map.component';
import {AgmCoreModule, GoogleMapsAPIWrapper} from '@agm/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {RequestInfoComponent} from './user/Content/my-requests/request-info/request-info.component';

const appRouts: Routes = [
  {
    path: 'welcome',
    component: WelcomeComponent,
    children: [
      {
        path: '',
        component: StartComponent
      },
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'register',
        component: RegistrationComponent
      },
      {
        path: '',
        component: StartComponent
      }
    ]
  },
  {
    path: 'user',
    component: UserComponent,
    canActivate: [RoutProtectionService],
    children: [
      {
        path: 'profile',
        component: ProfileComponent,
      },
      {
        path: 'browseTutors',
        component: TutorsBrowseComponent,
      },
      {
        path: 'myRequests',
        component: MyRequestsComponent,
      },
      {
        path: 'sendLessonRequest',
        component: LessonRequestComponent
      },
      {
        path: 'mySchedule',
        component: MyLessonsComponent
      },
      {
        path: 'paymentPage',
        component: PaymentComponent
      }
    ],
  },
  {
    path: '**',
    redirectTo: '/welcome'
  }
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
    TutorsBrowseComponent,
    TutorSearchPipe,
    LessonRequestComponent,
    MyRequestsComponent,
    MyLessonsComponent,
    PaymentComponent,
    MapComponent,
    RequestInfoComponent,
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
    NgxPaginationModule,
    CalendarModule.forRoot({provide: DateAdapter, useFactory: adapterFactory})
  ],
  providers: [{
    provide: HTTP_INTERCEPTORS,
    useClass: HttpInterceptorServiceService,
    multi: true
  }],
  bootstrap: [AppComponent],
})
export class AppModule {
}

