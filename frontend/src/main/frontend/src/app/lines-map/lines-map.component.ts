import { Component, OnInit } from '@angular/core';
import { MapsAPILoader, AgmMap } from '@agm/core';
import { GoogleMapsAPIWrapper } from '@agm/core/services';
import { ViewChild } from '@angular/core'
import { AgmDirectionModule } from 'agm-direction';
import { MatFormFieldModule, MatOptionModule, MatSelectModule } from
   '@angular/material';
import {LinesMapService} from './lines-map.service';


declare var google: any;
@Component({
  selector: 'app-lines-map',
  templateUrl: './lines-map.component.html',
  styleUrls: ['./lines-map.component.css']
})

export class LinesMapComponent implements OnInit {
  geocoder:any;
  @ViewChild(AgmMap) map: AgmMap;
  latLong: object;
  lines: object;
  visible: boolean = false;
  waypoints: object;
  public renderOptions = {
      suppressMarkers: true,
  }

  public markerOptions = {
      origin: {
          icon: 'https://www.shareicon.net/data/48x48/2015/09/15/641263_stop_512x512.png',
      },
      waypoints: {
          icon: 'https://www.shareicon.net/data/48x48/2015/09/15/641263_stop_512x512.png',
      },
      destination: {
          icon: 'https://www.shareicon.net/data/48x48/2015/09/15/641263_stop_512x512.png',
      },
  }
  constructor(public mapsApiLoader: MapsAPILoader,
              private wrapper: GoogleMapsAPIWrapper,
              private linesMapService: LinesMapService) {
    this.mapsApiLoader = mapsApiLoader;
    this.wrapper = wrapper;
    this.linesMapService = linesMapService;
    this.mapsApiLoader.load().then(() => {
      this.geocoder = new google.maps.Geocoder();
    });
  }

  ngOnInit() {
    this.linesMapService.getLineStations(1)
      .then(response => {
        console.log(response);
      });
    this.lines = [{name: 7, path: {source: {lat: 45.242036, lng: 19.842649}, destination: {lat: 45.264008, lng: 19.823417}}
    ,waypoints: [
      {
        location:{lat: 45.242036, lng: 19.842649},
        stopover: false,
      },
      {
        location:{lat: 45.252671, lng: 19.836873},
        stopover: false,
      },
      {
        location: {lat: 45.261041, lng: 19.832232},
        stopover: false,
      },
      {
        location:{lat: 45.264008, lng: 19.823417},
        stopover: false,
      }]
      },
      {name: 8, path: {source: {lat: 45.212036, lng: 19.842649}, destination: {lat: 45.204008, lng: 19.823417}},
        waypoints: [
          {
            location:{lat: 45.212036, lng: 19.842649},
            stopover: false
          },
          {
            location:{lat: 45.222671, lng: 19.836873},
            stopover: false
          },
          {
            location:{lat: 45.231041, lng: 19.832232},
            stopover: false
          },
          {
            location:{lat: 45.204008, lng: 19.823417},
            stopover: false
          }]}];
  }

  onChange(event) {
    this.visible = false;
    this.waypoints = event.value.waypoints;
    this.latLong = event.value.path;
  }

  onSubmit() {

  }

}