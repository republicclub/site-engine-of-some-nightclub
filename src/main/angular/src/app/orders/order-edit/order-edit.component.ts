import {Component, Input, OnInit} from '@angular/core';
import {MenuOrder} from "../../dto/menuOrder";
import {Table} from "../../dto/table";
import {MenuCategoryDto} from "../../dto/menuCategoryDto";
import {EventDto} from "../../dto/eventDto";
import {MenuService} from "../../menu.service";

@Component({
  selector: 'app-order-edit',
  templateUrl: './order-edit.component.html',
  styleUrls: ['./order-edit.component.css']
})
export class OrderEditComponent implements OnInit {

  @Input() order: MenuOrder;
  @Input() menuCategories: MenuCategoryDto[];
  @Input() events: EventDto[];
  @Input() tableNumbers: Table[];

  constructor(private menuService: MenuService) { }

  ngOnInit() {
  }

  changeEvent() {
    this.menuService.getAvailableTables(this.order.event).subscribe(responseArray => {
      let tableNumberForm = Array<Table>();
      if (Array.isArray(responseArray)) {
        responseArray.forEach(i => {
          let table = new Table();
          table.id = i;
          table.name = "Стол №" + i;
          tableNumberForm.push(table)
        });
      }
      this.tableNumbers = tableNumberForm;
    });
  }
}
