///<reference path="../../dto/ticketOrder.ts"/>
import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {TicketOrder} from "../../dto/ticketOrder";
import {OrderTicketService} from "../../services/tickets/order-ticket.service";
import {Event} from "../../dto/event";

export interface OrderTicketsModalData {
  event: Event;
}

export class TableDto {
  places: PlaceDto[];
  tableNumber: number;
}

class PlaceDto {
  status: string;
  placeNumber: number;
}

@Component({
  selector: 'app-order-tickets',
  templateUrl: './order-tickets.component.html',
  styleUrls: ['./order-tickets.component.css']
})
export class OrderTicketsComponent implements OnInit {
  orderFormGroup: FormGroup;
  ticketOrder: TicketOrder;
  tablesTopStairs: TableDto[];
  tablesMiddleStairs: TableDto[];
  tablesBottomStairs: TableDto[];
  tablesConsoleRight: TableDto[];
  tablesConsoleLeft: TableDto[];
  tablesEleven: TableDto[];
  tablesSeventeen: TableDto[];
  orderComplete: boolean;

  constructor(
    public dialogRef: MatDialogRef<OrderTicketsComponent>,
    @Inject(MAT_DIALOG_DATA) public data: OrderTicketsModalData,
    private _formBuilder: FormBuilder,
    private orderTicketService: OrderTicketService
  ) {
    this.ticketOrder = new TicketOrder();
    console.log(data);
    this.ticketOrder.event = this.data.event;
    console.log(this.ticketOrder.event);
    this.orderFormGroup = this._formBuilder.group({
      danceFloor: [this.ticketOrder.danceFloor, [Validators.required, Validators.min(0), Validators.max(20)]]
    });
    this.ticketOrder.danceFloor = 0;
    this.orderComplete = false;
  }

  onNoClick(): void {
    this.dialogRef.close();
  }


  payTicketOrder() {
    alert("Not implemented!!!!")
  }

  incrementDanceFloor() {
    if (this.ticketOrder.danceFloor < 21) {
      this.ticketOrder.danceFloor++;
    }
  }

  decrementDanceFloor() {
    if (this.ticketOrder.danceFloor > 0) {
      this.ticketOrder.danceFloor--;
    }
  }

  ngOnInit(): void {
    this.tablesTopStairs = [];
    this.tablesMiddleStairs = [];
    this.tablesBottomStairs = [];
    this.tablesConsoleRight = [];
    this.tablesConsoleLeft = [];
    this.tablesEleven = [];
    this.tablesSeventeen = [];


    this.orderTicketService.getTables(this.ticketOrder.event.id)
      .pipe()
      .subscribe(tablesData => {
        if (Array.isArray(tablesData)) {
          tablesData.forEach(tableData => {

            let table = this.getTable(tableData);
            switch (tableData.tableNumber) {
              case 1:
              case 2:
              case 3:
              case 4: {
                this.tablesTopStairs.push(table);
              }
                break;
              case 5:
              case 6:
              case 7:
              case 8:
              case 9:
              case 10: {
                this.tablesMiddleStairs.push(table);
              }
                break;
              case 11: {
                this.tablesEleven.push(table);
              }
                break;
              case 12:
              case 13:
              case 14:
              case 15:
              case 16: {
                this.tablesBottomStairs.push(table);
              }
                break;
              case 17: {
                this.tablesSeventeen.push(table);
              }
                break;
              case 18:
              case 19:
              case 20:
              case 21: {
                this.tablesConsoleLeft.push(table);
              }
                break;
              case 22:
              case 23:
              case 24:
              case 25: {
                this.tablesConsoleRight.push(table);
              }
                break;
              default : {
                alert("ERROR! Table numbers betwen 1 and 25.");
              }
            }
          })
        } else {
          alert("ERROR!!!!!!!")
        }
      });
  }

  private getTable(tableData) {
    let table = new TableDto();
    table.tableNumber = tableData.tableNumber;
    let places = [];
    if (Array.isArray(tableData.places)) {
      tableData.places.forEach(placeData => {
        let place = new PlaceDto();
        place.placeNumber = placeData.placeNumber
        place.status = placeData.status;
        places.push(place);
      });
    }
    table.places = places;
    return table;
  }
}

