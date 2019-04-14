import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {
  ClubEventTicketPriceBaseListResultDto,
  ClubEventTicketPriceDto
} from "../../dto/tickets/clubEventTicketPriceDto";
import {ResponseEntity} from "../../dto/ResponseEntity";

@Injectable()
export class PriceService {
  private priceAdminUrl: string = '/api/admin/events/ticket/price';

  constructor(private http: HttpClient) {
  }

  getPrices(eventId: number) {
    let url = this.priceAdminUrl + '?eventId=' + eventId;
    return this.http.get<ClubEventTicketPriceBaseListResultDto>(url);
  }

  delete(eventId: number) {
    let url = this.priceAdminUrl + '?priceId=' + eventId;
    return this.http.delete<ResponseEntity>(url);
  }

  save(price: ClubEventTicketPriceDto) {
    return this.http.post<ResponseEntity>(this.priceAdminUrl,price);
  }
}
