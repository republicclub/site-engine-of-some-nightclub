import {Component, EventEmitter, Input, OnInit, Output, ViewContainerRef} from '@angular/core';
import {Event} from "../../dto/event";
import * as moment from "moment";
import {ToastsManager} from "ng6-toastr";
import {MatDialog} from "@angular/material";
import {OrderTicketsComponent} from "../../orders/order-tickets/order-tickets.component";
import {humanizeBytes, UploaderOptions, UploadFile, UploadInput, UploadOutput} from 'ngx-uploader';
import {EventTicketsReportDto} from "../../dto/tickets/eventTicketsReportDto";

@Component({
  selector: 'app-event-view',
  templateUrl: './event-view.component.html',
  styleUrls: ['./event-view.component.css']
})
export class EventViewComponent implements OnInit {
  viewEvent: boolean;
  @Output() onEventSaveButtonClick = new EventEmitter();
  @Output() onEventDeleteButtonClick = new EventEmitter();
  @Output() onEventCloseButtonClick = new EventEmitter();
  @Input() event: Event = new Event();
  @Input() readOnly: boolean;
  @Input() tickets: EventTicketsReportDto;

  options: UploaderOptions;
  files: UploadFile[];
  uploadInput: EventEmitter<UploadInput>;
  humanizeBytes: Function;

  constructor(public toastr: ToastsManager, vcr: ViewContainerRef, public dialog: MatDialog) {
    this.toastr.setRootViewContainerRef(vcr);
    this.options = {concurrency: 1, maxUploads: 300};
    this.files = []; // local uploading files array
    this.uploadInput = new EventEmitter<UploadInput>(); // input events, we use this to emit data to ngx-uploader
    this.humanizeBytes = humanizeBytes;
  }

  ngOnInit() {
    this.viewEvent = true;
    if (!this.tickets) {
      this.tickets = new EventTicketsReportDto();
    }
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
    if (this.event.id > 0) {
      if (confirm("Удалить событие?")) {
        this.onEventDeleteButtonClick.emit(this.event.id)
      }
    } else {
      alert("Событие не сохранено. Удалить не возможно.")
    }
  }

  closeEvent() {
    if (confirm("Закрыть редактирование события?")) {
      this.onEventCloseButtonClick.emit()
    }
  }

  goToGoogleCalendar() {
    const location = 'ул.+Притыцкого+62,+Минск,+REPUBLIC';
    const pattern = 'YYYYMMDDTHHmmSS';
    const start = moment(this.event.startEvent).format(pattern);
    const end = moment(this.event.endEvent).format(pattern);
    const url = `https://calendar.google.com/calendar/render?action=TEMPLATE&dates=${start}/${end}&location=${location}&text=${this.event.name}++|++RE:PUBLIC&details=%3Ca%20href=%22https://republic-club.by/event/${this.event.id}`;
    window.open(url).focus();
  }

  openOrderModalWindow() {
    const dialogRef = this.dialog.open(OrderTicketsComponent, {
      data: {
        event: this.event
      }
    });
  }

  onUploadOutput(output: UploadOutput): void {
    switch (output.type) {
      case 'addedToQueue' : {
        if (typeof output.file !== 'undefined') {
          this.files.push(output.file);
        }
      } break;
      case 'uploading' : {
        if (typeof output.file !== 'undefined') {
          const index = this.files.findIndex(file => typeof output.file !== 'undefined' && file.id === output.file.id);
          this.files[index] = output.file;
        }
      } break;
      case 'done' : {
        const response = output.file.response;
        if (response.filePath) {
          this.event.coverUri = '/files/' + response.filePath;
        }
        document.getElementById('coverUri')['value'] = null;
      }
        break;
      default : {
        console.log(output.type);
      }
    }
  }

  startUpload(): void {
    const event: UploadInput = {
      type: 'uploadAll',
      url: '/api/file/upload',
      method: 'POST',
      data: {foo: 'bar'}
    };

    this.uploadInput.emit(event);
  }

}
