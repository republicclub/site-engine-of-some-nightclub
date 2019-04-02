import {Component, OnInit} from '@angular/core';
import {EventsService} from "../../events.service";
import {EventDto} from "../../dto/eventDto";

@Component({
  selector: 'app-disco',
  templateUrl: './disco.component.html',
  styleUrls: ['./disco.component.css']
})
export class DiscoComponent implements OnInit {
  eventFirst: EventDto;
  eventSecond: EventDto;
  eventThird: EventDto;

  constructor(private eventService: EventsService) {
  }

  ngOnInit() {
    this.eventFirst = new EventDto();
    this.eventSecond = new EventDto();
    this.eventThird = new EventDto();

    this.eventService.getDiscoEvents()
      .subscribe(responce => {
        this.eventFirst = responce.items[0];
        this.eventSecond = responce.items[1];
        this.eventThird = responce.items[2];
      });
  }
}
