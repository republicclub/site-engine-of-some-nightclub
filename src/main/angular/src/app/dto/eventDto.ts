export class EventDto {
  id: number;
  cost: number;
  costText: string;
  description: string;
  startEvent: string;
  endEvent: string;
  name: string;
  coverUri: string;
  buyTicketUrl: string;
  recommendation: boolean;
  republicPay: boolean;
  costDance: number;
  costTablePlace: number;
}

export class EventListResult {
  items: EventDto[];
  total: number
}
