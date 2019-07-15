import {EventDto} from "./eventDto";
import {TableDto} from "../orders/order-tickets/order-tickets.component";
import {MenuFoodOrder} from "./menuFoodOrder";

export class TicketOrder {
  public totalMoney : number;
  public danceFloor: number;
  public event: EventDto;
  public placeSeats: number;
  public name: string;
  public surname: string;
  public description: string;
  public email: string;
  public phone: string;
  public rulesCheck: boolean;
  public offerCheck: boolean;
  public tables: TableDto[];
  public menuFoodOrder: MenuFoodOrder;

}
