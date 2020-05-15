import {Component, OnInit} from '@angular/core';
import {HttpClient, ɵangular_packages_common_http_http_a} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {Time} from '@angular/common';
import {LoginService} from '../../../service/login/login.service';
import {NgbModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-lesson-request',
  templateUrl: './lesson-request.component.html',
  styleUrls: ['./lesson-request.component.scss']
})
export class LessonRequestComponent implements OnInit {

  username = '';
  lessonRequest: LessonRequest = {
    lat: null,
    lng: null,
    price: null,
    message: null,
    date: null,
    time: null,
    tutorId: null,
    studentId: this.loginService.getLoggedInUserName()
  };

  constructor(private http: HttpClient, private route: ActivatedRoute,
              private loginService: LoginService) {
    this.username = this.route.snapshot.queryParamMap.get('username');
    this.lessonRequest.tutorId = this.username;
  }

  sendRequest() {
    const url = 'http://localhost:8080/sendRequest';
    alert(JSON.stringify(this.lessonRequest));
    this.http.post<string>(url, JSON.stringify(this.lessonRequest)).subscribe(
      res => {
        alert(res);
      });
  }

  ngOnInit(): void {
  }

  setCoors(coords: google.maps.LatLng) {
    this.lessonRequest.lng = coords.lng();
    this.lessonRequest.lat = coords.lat();
  }
}


export interface LessonRequest {
  lng: number;
  lat: number;
  price: number;
  date: Date;
  time: Time;
  message: string;
  tutorId: string;
  studentId: string;

}
