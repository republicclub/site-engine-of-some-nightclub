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
  private eventService: EventsService;
  event: EventDto;

  constructor(private activatedRoute: ActivatedRoute, eventService: EventsService) {
    this.eventService = eventService;
  }

  ngOnInit() {
    this.event = new EventDto();
    let id = +this.activatedRoute.snapshot.paramMap.get('id');
    this.eventService.getEvent(id)
      .subscribe(responce => {
        this.event = responce;
        if (!this.event.buyTicketUrl) {
          this.event.buyTicketUrl = "/cashbox";
        }
      });
  }

}
