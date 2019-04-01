import {Component, OnInit} from '@angular/core';
import {EventsService} from "../events.service";
import {EventDto} from "../dto/eventDto";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-event-page',
  templateUrl: './event-page.component.html',
  styleUrls: ['./event-page.component.css']
})
export class EventPageComponent implements OnInit {
  event: EventDto = new EventDto();

  constructor(private activatedRoute: ActivatedRoute, private eventService: EventsService) {
  }

  ngOnInit() {
    let id = +this.activatedRoute.snapshot.paramMap.get('id');
    this.eventService.getEvent(id)
      .subscribe(response => {
        this.event = response;
        if (!this.event.buyTicketUrl) {
          this.event.buyTicketUrl = "/cashbox";
        }
      });
  }

}
