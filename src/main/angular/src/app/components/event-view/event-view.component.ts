import {Component, EventEmitter, Input, OnInit, Output, ViewContainerRef} from '@angular/core';
import {Event} from "../../dto/event";
import * as moment from "moment";
import {ToastsManager} from "ng6-toastr";

@Component({
  selector: 'app-event-view',
  templateUrl: './event-view.component.html',
  styleUrls: ['./event-view.component.css']
})
export class EventViewComponent implements OnInit {
  viewEvent: boolean;
  @Output() onEventSaveButtonClick = new EventEmitter();
  @Output() onEventDeleteButtonClick = new EventEmitter();
  @Input() event: Event = new Event();
  @Input() readOnly: boolean;

  constructor(public toastr: ToastsManager, vcr: ViewContainerRef) {
    this.toastr.setRootViewContainerRef(vcr);
  }

  ngOnInit() {
    this.viewEvent = true;
  }

  setViewEvent(b: boolean) {
    this.viewEvent = b;
  }

  saveEvent(event: Event) {
    this.onEventSaveButtonClick.emit(event);
  }

  setIdZero() {
    this.event.id = null;
  }

  get startEventText() {
    return moment(this.event.startEvent).format('dddd[,] LL[,] LT')
  }

  deleteEvent() {
    console.log(this.event.id);
    if (this.event.id > 0) {
      if (confirm("Удалить событие?")) {
        this.onEventDeleteButtonClick.emit(this.event.id)
      }
    } else {
      alert("Событие не сохранено. Удалить не возможно.")
    }

  }
}
