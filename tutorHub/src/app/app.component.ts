import {Component, OnInit} from '@angular/core';
import {LoginService} from './service/login/login.service';
import {ReedirectorService} from './service/login/reedirector.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  hide = false;

  constructor(private evtSvc: LoginService, private redir: ReedirectorService) {
  }

  ngOnInit() {
    this.redir.childEventListner().subscribe(info => {
      this.hide = info;
    });
  }
}
