import { Component, OnInit } from '@angular/core';
import {Time} from '@angular/common';
import {HttpClient, HttpParams} from '@angular/common/http';
import {LoginService} from '../../../service/login/login.service';
import {UserInfo} from '../profile/profile.component';
import {map} from 'rxjs/operators';
import {Tutor} from '../tutors-browse/tutors-browse.component';

@Component({
  selector: 'app-my-requests',
  templateUrl: './my-requests.component.html',
  styleUrls: ['./my-requests.component.scss']
})
export class MyRequestsComponent implements OnInit {
  requests: LessonRequest[] = [];
  username: string;

  constructor(private http: HttpClient, private loginService: LoginService) {
    this.username = loginService.getLoggedInUserName();
  }

  ngOnInit(): void {
    const url = 'http://localhost:8080/loadMyRequests';
    const params = new HttpParams().set('username', this.username);
    this.http.get<LessonRequest[]>(url, {params})
      .subscribe(tutors => {
        console.log(tutors);
        this.requests = tutors;
        console.log(this.requests);
      });
  }
  reject(requestID: number) {
    this.http.post('http://localhost:8080/rejectRequest/' + requestID, {}).subscribe();
    this.deleteFromCurrentArray(requestID);
  }
  approve(requestID: number) {
    this.http.post('http://localhost:8080/approveRequest/' + requestID, {}).subscribe();
    this.deleteFromCurrentArray(requestID);
  }

  deleteFromCurrentArray(id: number) {
    for (const request of this.requests) {
      if (id === request.requestID) {
        this.requests.splice(this.requests.indexOf(request), 1);
        break;
      }
    }
  }
}

export interface LessonRequest {
  tutorID: string;
  place: string;
  pricePerHour: number;
  time: Time;
  date: Date;
  requestID: number;
}
