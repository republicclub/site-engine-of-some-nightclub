export class ClubEventTicketPriceDto {
  id: number;
  cost: number;
  quantity: number;
  typePrice: string;
  eventId: number;
  startActiveTime: Date;
  endActiveTime: Date;
  modifiedBy: string;
}

export class ClubEventTicketPriceBaseListResultDto {
  items: ClubEventTicketPriceDto[];
  total: number
}
