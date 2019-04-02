import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {EventsService} from "../../../events.service";
import {EventDto} from "../../../dto/eventDto";
import {ClubEventTicketPriceDto} from "../../../dto/tickets/clubEventTicketPriceDto";
import {PriceService} from "../../../services/price/price.service";
import {ConfirmationDialogService} from "../../../common-components/confirmation-dialog/confirmation-dialog.service";
import {UserPersonDataService} from "../../../user-cabinet/user-person-data.service";
import * as moment from "moment";

@Component({
  selector: 'app-admin-event-price',
  templateUrl: './admin-event-price.component.html',
  styleUrls: ['./admin-event-price.component.css']
})
export class AdminEventPriceComponent implements OnInit {
  eventId: number;
  event: EventDto;
  prices: ClubEventTicketPriceDto[];
  editMode: boolean;
  priceEditId: number;
  hideStartActiveTimePicker: boolean;
  hideEndActiveTimePicker: boolean;
  DATE_TIME_PATTERN: string = 'yyyy-MM-dd HH:mm:ss';

  constructor(
    private activatedRoute: ActivatedRoute,
    private eventsService: EventsService,
    private priceService: PriceService,
    private confirmationDialogService: ConfirmationDialogService,
    private userService: UserPersonDataService
  ) {

  }

  ngOnInit() {
    this.eventId = +this.activatedRoute.snapshot.paramMap.get('id');
    this.event = new EventDto();
    this.event.id = -1;
    this.editMode = false;
    this.priceEditId = -2;
    this.hideStartActiveTimePicker = true;
    this.hideEndActiveTimePicker = true
    this.eventsService.getEvent(this.eventId)
      .pipe()
      .subscribe(value => {
        this.event = value;
      })
    this.priceService.getPrices(this.eventId)
      .pipe()
      .subscribe(value => {
        console.log("Retrieve prices. Start")
        console.log(value.items)
        for (let price of value.items) {
          price.startActiveTime = moment.utc(price.startActiveTime).local().toDate()
          price.endActiveTime = moment.utc(price.endActiveTime).local().toDate()
        }
        this.prices = value.items;
        this.prices.sort((a, b) => {
          var c = a.typePrice.charCodeAt(0) - b.typePrice.charCodeAt(0);
          if (c == 0) {
            c = (a.startActiveTime.getTime() - b.startActiveTime.getTime())
          }
          return c;
        })
        console.log("Retrieve prices. End")
        console.log(this.prices)
      })
  }

  create() {
    console.log("Create. Start")
    let dto = new ClubEventTicketPriceDto();
    dto.id = 0;
    dto.cost = 0
    dto.quantity = 0
    dto.eventId = this.eventId
    this.userService.retrieveUser()
      .pipe()
      .subscribe(value => {
        dto.modifiedBy = value.name + ' ' + value.surname
      })
    dto.startActiveTime = new Date()
    dto.endActiveTime = new Date()
    this.prices.unshift(dto)
    this.editPrice(dto)
    console.log("Create. End")
  }

  editPrice(price: ClubEventTicketPriceDto) {
    console.log("Edit Price. Start")
    this.editMode = true
    this.priceEditId = price.id
    console.log("Edit Price. End")
  }

  deletePrice(price: ClubEventTicketPriceDto) {
    this.confirmationDialogService.confirm('Пожалуйста подтвердите удаление', 'Вы на самом деле хотите удалить эту запись?')
      .then((confirmed) => {
        if (confirmed) {
          this.priceService.delete(price.id)
            .pipe()
            .subscribe(response => {
              if (response.success) {
                this.ngOnInit();
              } else {
                alert(response.message)
              }
            })
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }

  isHiddenPriceFormField(price: ClubEventTicketPriceDto) {
    return this.editMode && this.priceEditId == price.id
  }

  savePrice(price: ClubEventTicketPriceDto) {
    console.log(price);
    this.confirmationDialogService.confirm('Пожалуйста подтвердите сохранение', 'Вы желаете сохранить данное меропритие.')
      .then((confirmed) => {
        if (confirmed) {
          this.priceService.save(price)
            .pipe()
            .subscribe(response => {
              if (response.success) {
                this.ngOnInit();
              } else {
                alert(response.message)
              }
            })
        }
      })
      .catch(() => console.log('User dismissed the dialog (e.g., by using ESC, clicking the cross icon, or clicking outside the dialog)'));
  }
}
