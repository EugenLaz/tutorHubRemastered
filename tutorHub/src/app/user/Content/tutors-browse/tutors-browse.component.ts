import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
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
  page = 0;
  @Input() searchValue = '';
  @Output() searchModelChange: EventEmitter<any> = new EventEmitter();

  constructor(private http: HttpClient) {
  }

  ngOnInit(): void {
    this.loadTutors();
  }

  public loadTutors() {
    const url = 'http://localhost:8080/loadTutors';
    this.http.get<Tutor[]>(url).pipe(
      map(tutors => tutors.map(({name, rating, personalInfo, username}) => ({name, rating, personalInfo, username}))),
    )
      .subscribe(tutors => {
        console.log(tutors);
        this.tutors = tutors;
        console.log(this.tutors);
      });
  }

  updateSearchModel(value) {
    this.searchValue = value;
    this.searchModelChange.emit(this.searchValue);
  }


}

export interface Tutor {
  name: string;
  rating: number;
  personalInfo: string;
  username: string;
}
