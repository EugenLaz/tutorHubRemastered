import {AfterViewInit, Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {LessonRequest} from '../my-requests.component';
import {NgbActiveModal} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-request-info',
  templateUrl: './request-info.component.html',
  styleUrls: ['./request-info.component.scss']
})
export class RequestInfoComponent implements AfterViewInit {
  @Input() requestInfo: LessonRequest;

  @ViewChild('mapContainer', {static: false}) gmap: ElementRef;
  map: google.maps.Map;
  lat;
  lng;

  coordinates: google.maps.LatLng;
  mapOptions: google.maps.MapOptions;
  marker: google.maps.Marker;


  constructor(public activeModal: NgbActiveModal) {
  }

  ngAfterViewInit() {
    this.coordinates = new google.maps.LatLng(this.requestInfo.lat, this.requestInfo.lng);
    this.mapOptions = {
      center: this.coordinates,
      zoom: 8
    };
    this.marker = new google.maps.Marker({
      position: this.coordinates,
      map: this.map,
    });
    this.mapInitializer();
  }

  mapInitializer() {
    this.map = new google.maps.Map(this.gmap.nativeElement,
      this.mapOptions);
    this.marker.addListener('click', () => {
      this.marker.setMap(null);
    });
    this.marker.setMap(this.map);
  }
}
