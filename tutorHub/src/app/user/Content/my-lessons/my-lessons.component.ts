import {ChangeDetectionStrategy, Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import {CalendarEvent, CalendarEventAction, CalendarEventTimesChangedEvent, CalendarView} from 'angular-calendar';
import {Subject} from 'rxjs';
import {startOfDay, endOfDay, subDays, addDays, endOfMonth, isSameDay, isSameMonth, addHours} from 'date-fns';
import {map} from 'rxjs/operators';
import {Tutor} from '../tutors-browse/tutors-browse.component';
import {HttpClient, HttpParams} from '@angular/common/http';
import {LoginService} from '../../../service/login/login.service';
import {ActivatedRoute} from '@angular/router';

const colors: any = {
  red: {
    primary: '#ad2121',
    secondary: '#FAE3E3',
  },
  blue: {
    primary: '#1e90ff',
    secondary: '#D1E8FF',
  },
  yellow: {
    primary: '#e3bc08',
    secondary: '#FDF1BA',
  },
};




@Component({
  selector: 'app-my-lessons',
  changeDetection: ChangeDetectionStrategy.OnPush,
  templateUrl: './my-lessons.component.html',
  styleUrls: ['./my-lessons.component.scss']
})
export class MyLessonsComponent {
  @ViewChild('modalContent', { static: true }) modalContent: TemplateRef<any>;
  view: CalendarView = CalendarView.Month;
  CalendarView = CalendarView;
  viewDate: Date = new Date();
  modalData: {
    action: string;
    event: CalendarEvent;
  };
  refresh: Subject<any> = new Subject();
  events: CalendarEvent[] = [
  ];
  activeDayIsOpen = false;


  constructor(private modal: NgbModal, private http: HttpClient, private loginService: LoginService, route: ActivatedRoute) {
    route.params.subscribe(val => {
      const url = 'http://localhost:8080/laodSchedule';
      const username = this.loginService.getLoggedInUserName();
      const params = new HttpParams().set('username', username);
      this.http.get<CalendarEvent[]>(url, {params}).pipe(
        map(events => events.map(({ start, title}) =>
          ({ start: addHours(startOfDay(new Date(start.toString())), new Date(start.toString()).getHours()), title }))))
        .subscribe(events => {
          console.log(events);
          this.events = events;
          console.log(this.events); });
    });
  }


  dayClicked({ date, events }: { date: Date; events: CalendarEvent[] }): void {
    if (isSameMonth(date, this.viewDate)) {
      if (
        (isSameDay(this.viewDate, date) && this.activeDayIsOpen === true) ||
        events.length === 0
      ) {
        this.activeDayIsOpen = false;
      } else {
        this.activeDayIsOpen = true;
      }
      this.viewDate = date;
    }
  }

  eventTimesChanged({
                      event,
                      newStart,
                      newEnd,
                    }: CalendarEventTimesChangedEvent): void {
    this.events = this.events.map((iEvent) => {
      if (iEvent === event) {
        return {
          ...event,
          start: newStart,
          end: newEnd,
        };
      }
      return iEvent;
    });
  }


  setView(view: CalendarView) {
    this.view = view;
  }

  closeOpenMonthViewDay() {
    this.activeDayIsOpen = false;
  }


}
