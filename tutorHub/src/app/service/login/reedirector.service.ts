import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ReedirectorService {
  private childClickedEvent = new BehaviorSubject<boolean>(false);

  emitChildEvent(sucess: boolean) {
    this.childClickedEvent.next(sucess);
  }

  childEventListner() {
    return this.childClickedEvent.asObservable();
  }
}
