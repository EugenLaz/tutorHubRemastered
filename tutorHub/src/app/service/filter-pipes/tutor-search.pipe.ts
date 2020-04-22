import { Pipe, PipeTransform } from '@angular/core';
import {Tutor} from '../../user/Content/tutors-browse/tutors-browse.component';

@Pipe({
  name: 'tutorSearch'
})
export class TutorSearchPipe implements PipeTransform {

  transform(list: Tutor[], filterText: string): any  {
    return list ? list.filter(item => item.name.search(new RegExp(filterText, 'i')) > -1) : [];
  }

}
