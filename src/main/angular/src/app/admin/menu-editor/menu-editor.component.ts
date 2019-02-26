import {Component, OnInit} from '@angular/core';
import {MenuCategoryDto} from "../../dto/menuCategoryDto";
import {MenuService} from "../../menu.service";
import {MenuItemPriceDto} from "../../dto/menuItemPriceDto";

class MenuEditorWrapper {
  categoryEditDto: MenuCategoryDto;
  editItemPositionId: number;
  itemPosition: MenuItemPriceDto;
}

@Component({
  selector: 'app-menu-editor',
  templateUrl: './menu-editor.component.html',
  styleUrls: ['./menu-editor.component.css']
})
export class MenuEditorComponent implements OnInit {

  categories: MenuCategoryDto[];
  orderFood: any;
  editCategoryId: number;
  editWrapper: MenuEditorWrapper;

  constructor(private menuService: MenuService) {
  }

  ngOnInit() {
    this.loadMenu();
  }

  loadMenu() {
    this.menuService.getMenu().subscribe(c => {
      this.categories = c;
      this.editWrapper = new MenuEditorWrapper();
      this.editCategoryId = -1;
      this.editWrapper.categoryEditDto = new MenuCategoryDto();
      this.editWrapper.editItemPositionId = -1;
      this.editWrapper.itemPosition = new MenuItemPriceDto();
      this.editWrapper.itemPosition.id = 0;
    });
  }

  onClickeditCategory(category: MenuCategoryDto) {
    this.editCategoryId = category.id;
    this.editWrapper.categoryEditDto = category;
  }

  onClickSaveCategory(category: MenuCategoryDto) {
    this.editCategoryId = 0;
    this.menuService.saveCategory(category)
      .subscribe(responce => {
        if (responce.success) {
          this.loadMenu();
          this.editCategoryId = -1;
        }
      });
  }

  onClickNewCategory(categoryMain: MenuCategoryDto) {
    this.editCategoryId = -1;
    this.editWrapper.categoryEditDto = new MenuCategoryDto();
    this.editWrapper.categoryEditDto.parentId = categoryMain.id;
    this.onClickeditCategory(this.editWrapper.categoryEditDto);
  }

  onClickEditMenuItem(item: MenuItemPriceDto) {
    this.editWrapper.editItemPositionId = item.id;
    this.editWrapper.itemPosition = item;
  }

  onClickSaveItemPosition(item: MenuItemPriceDto) {
    this.menuService.saveItemPosition(item)
      .subscribe(responce => {
        this.editWrapper.editItemPositionId = 0;
        this.editWrapper.itemPosition = new MenuItemPriceDto();
        this.loadMenu();
      });

  }

  onClickCreateMenuItem(category: MenuCategoryDto) {
    let mip = new MenuItemPriceDto();
    mip.id = -1;
    mip.categoryId = category.id;
    this.onClickEditMenuItem(mip)
  }
}
