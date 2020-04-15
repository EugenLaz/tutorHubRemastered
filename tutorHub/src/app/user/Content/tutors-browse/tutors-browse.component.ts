import { Component, OnInit } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {map, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';

@Component({
  selector: 'app-tutors-browse',
  templateUrl: './tutors-browse.component.html',
  styleUrls: ['./tutors-browse.component.scss']
})
export class TutorsBrowseComponent implements OnInit {

  tutors: Tutor[] = [];
  indexOfLastTutor = 20;
  indexOfFirstTutor = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.loadTutors();
  }

  public loadTutors() {
    const url = 'http://localhost:8080/loadTutors';
    let params = new HttpParams().set('firstTutorIndex', String(this.indexOfFirstTutor) )
      .set('lastTutorIndex', String(this.indexOfLastTutor));
    this.http.get<Tutor[]>(url, {params}).pipe(
      map(tutors => tutors.map(({ name, rating, personalInfo }) => ({ name, rating, personalInfo }))),
    )
      .subscribe(tutors => {
        console.log(tutors);
        this.tutors = tutors;
        console.log(this.tutors);
      });
  }
}
export interface Tutor {
  name: string;
  rating: number;
  personalInfo: string;
}
