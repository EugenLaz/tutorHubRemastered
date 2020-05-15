/// <reference types="@types/googlemaps" />
import {AfterViewInit, Component, ElementRef, EventEmitter, Output, ViewChild} from '@angular/core';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit {
  @ViewChild('mapContainer', {static: false}) gmap: ElementRef;
  map: google.maps.Map;
  lat = 50.2716;
  lng = 30.3125;

  coordinates: google.maps.LatLng;
  mapOptions: google.maps.MapOptions;
  marker: google.maps.Marker;

  @Output() nameUpdate: EventEmitter<google.maps.LatLng> = new EventEmitter<google.maps.LatLng>();

  constructor() {
    this.coordinates = new google.maps.LatLng(this.lat, this.lng);
    this.mapOptions = {
      center: this.coordinates,
      zoom: 8
    };
    this.marker = new google.maps.Marker({
      position: this.coordinates,
      map: this.map,
    });
  }

  updateParent(coordinates) {
    this.nameUpdate.emit(coordinates);
  }

  ngAfterViewInit() {
    this.mapInitializer();
  }

  mapInitializer() {
    this.map = new google.maps.Map(this.gmap.nativeElement,
      this.mapOptions);
    this.marker.addListener('click', () => {
      this.marker.setMap(null);
    });
    this.map.addListener('click', (mapClick) => this.addMarker(mapClick.latLng.lat(), mapClick.latLng.lng()));
    this.marker.setMap(this.map);
  }

  addMarker(lat: number, lng: number) {
    this.lat = lat;
    this.lng = lng;
    const position = new google.maps.LatLng(this.lat, this.lng);
    this.marker.setPosition(position);
    this.marker.setMap(this.map);
    this.updateParent(position);
  }


}

