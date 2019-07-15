import {
  Component,
  Input,
  OnInit
} from '@angular/core';
import {MenuCategoryDto} from "../../dto/menuCategoryDto";
import {MenuFoodOrder} from "../../dto/menuFoodOrder";
import {MenuItemPriceDto} from "../../dto/menuItemPriceDto";
import {MenuService} from "../../menu.service";

@Component({
  selector: 'app-order-menu-food',
  templateUrl: './order-menu-food.component.html',
  styleUrls: ['./order-menu-food.component.css']
})
export class OrderMenuFoodComponent implements OnInit {
  menuCategories: MenuCategoryDto[];
  @Input() order: MenuFoodOrder;
  constructor(private menuService: MenuService) { }

  ngOnInit() {
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
    });
  }

  increment(item: MenuItemPriceDto) {
    item.count = item.count + 1;
    this.storeCountInModel(item);
  }

  decrement(item: MenuItemPriceDto) {
    item.count = item.count - 1;
    if (item.count < 0) {
      item.count = 0;
    }
    this.storeCountInModel(item);
  }

  private storeCountInModel(item: MenuItemPriceDto) {
    this.order.food[item.itemPriceId] = item.count;
    this.order.foodPrice[item.itemPriceId] = item.price;
  }

  get totalMoney() {
    let total = 0;
    for (let foodKey in this.order.food) {
      total += this.order.foodPrice[foodKey] * this.order.food[foodKey];
    }
    this.order.totalMoney = total;
    return total;
  }
}
