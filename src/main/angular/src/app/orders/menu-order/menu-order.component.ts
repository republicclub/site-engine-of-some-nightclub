import {
  Component,
  OnInit
} from '@angular/core';
import {FormBuilder} from '@angular/forms';
import {MenuCategoryDto} from "../../dto/menuCategoryDto";
import {MenuService} from "../../menu.service";

@Component({
  selector: 'app-menu-order',
  templateUrl: './menu-order.component.html',
  styleUrls: ['./menu-order.component.css']
})
export class MenuOrderComponent implements OnInit {
  menuCategories: MenuCategoryDto[];

  constructor(private menuService: MenuService, private _formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.menuService.getMenuPageSummary().subscribe(menuPage => {
      this.menuCategories = menuPage.categories;
    });
  }
}
