import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {MenuCategoryDto} from "../../dto/menuCategoryDto";
import {EventDto} from "../../dto/eventDto";
import {MenuService} from "../../menu.service";
import * as moment from "moment";
import {MenuOrder} from "../../dto/menuOrder";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material";


export interface OrderModalData {
  orderId: number;
}

@Component({
  selector: 'app-menu-order',
  templateUrl: './menu-order.component.html',
  styleUrls: ['./menu-order.component.css']
})
export class MenuOrderComponent implements OnInit {
  menuFormGroup: FormGroup;
  orderDetailsFormGroup: FormGroup;

  menuCategories: MenuCategoryDto[];
  events: EventDto[];
  order = new MenuOrder();
  orderComplete: boolean;

  constructor(private menuService: MenuService, private _formBuilder: FormBuilder, public dialog: MatDialog) {
  }

  ngOnInit() {
    this.orderComplete = false;
    this.menuFormGroup = this._formBuilder.group({
      firstCtrl: ['', Validators.required]
    });
    this.orderDetailsFormGroup = this._formBuilder.group({
      secondCtrl: ['', Validators.required]
    });
    this.order.food = {};
    this.order.foodPrice = {};
    this.order.event = 0;
    this.retrieveMenuData();
  }

  private retrieveMenuData() {
    this.menuService.getMenuPageSummary().subscribe(menuPage => {
      this.menuCategories = menuPage.categories;
      this.menuCategories.forEach(mainCategory => {
        mainCategory.categories.forEach(category => {
          if (category.menuItems) {
            category.menuItems.forEach(item => {
              item.count = 0;
            })
          }
        })
      });
      this.events = menuPage.events;
      this.events.forEach(event => {
        event.startEvent = moment(event.startEvent).format('D MMMM');
      })
    });
  }

  submitOrder() {
    this.menuService.storeOrder(this.order)
      .subscribe(response => {
        this.order = response;
        this.orderComplete = true;
      });
  }

  payOrder() {
    this.menuService.storeOrder(this.order)
      .subscribe(responseOrder => {
        if (responseOrder.id > 0) {
          this.order = responseOrder;
          const dialogRef = this.dialog.open(BepaidDialog, {
            data: {
              orderId: this.order.id
            }
          });
        } else {
          debugger;
        }
      });
  }
}

@Component({
  selector: 'dialog-bepaid-dialog',
  templateUrl: 'dialog-bepaid-dialog-template.html'
})
export class BepaidDialog {

  constructor(
    public dialogRef: MatDialogRef<BepaidDialog>,
    @Inject(MAT_DIALOG_DATA) public data: OrderModalData) {}

  onNoClick(): void {
    alert("close")
    this.dialogRef.close();
  }
}
