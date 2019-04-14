import {EventDto} from "./eventDto";
import {MenuCategoryDto} from "./menuCategoryDto";

export class MenuPageSummary {
  categories: MenuCategoryDto[];
  events: EventDto[];
}
