import {EventRelevant} from "./eventRelevant";
import {EventReport} from "./eventGallery";
import {EventDto} from "./eventDto";

export class MainEventPage {
  today: EventDto[];
  tomorrow: EventDto[];
  currentAndNextWeek: EventDto[];
  currentMonth: EventDto[];
  nextMonth: EventDto[];
  nextNextMonth: EventDto[];
  relevant: EventRelevant[];
  gallery: EventReport[];
}


