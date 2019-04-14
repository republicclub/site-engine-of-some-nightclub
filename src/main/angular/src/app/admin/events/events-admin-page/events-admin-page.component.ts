import {Component, OnInit} from '@angular/core';
import {EventDto} from "../../../dto/eventDto";
import {EventsService} from "../../../events.service";
import {EventTicketsReportDto} from "../../../dto/tickets/eventTicketsReportDto";
import {OrderTicketService} from "../../../services/tickets/order-ticket.service";

@Component({
  selector: 'app-events-admin-page',
  templateUrl: './events-admin-page.component.html',
  styleUrls: ['./events-admin-page.component.css']
})
export class EventsAdminPageComponent implements OnInit {
  viewEvents: boolean;
  viewEvent: boolean;
  event: EventDto;
  tickets: EventTicketsReportDto;

  constructor(private eventsService: EventsService, private orderTicketService: OrderTicketService) {
  }

  ngOnInit() {
    this.viewEvents = true;
    this.viewEvent = false;
    this.event = new EventDto();
    this.tickets = new EventTicketsReportDto();
  }

  handleRowSelected(row: EventDto) {
    this.event = row;
    this.viewEvent = true;
    this.viewEvents = false;
    this.orderTicketService.getTicketsReport(this.event.id)
      .pipe()
      .subscribe(ticketsReport => {
        if (ticketsReport.success) {
          this.tickets = ticketsReport.data;
        }
      })
  }

  createNewEvent() {
    this.event = new EventDto();
    this.viewEvent = true;
    this.viewEvents = false;
  }

  saveEvent(event: EventDto) {
    this.eventsService.save(event).subscribe(event => {
      this.event = event;
    });
  }

  deleteEvent(eventId: number) {
    this.eventsService.deleteEvent(eventId)
      .subscribe(data=> {
        if (!!data.success) {
          alert("Удалено!");
          this.viewEvents = true;
          this.viewEvent = false;
          this.event = new EventDto();
        }
      });
  }
}
