import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

import {Observable, of as observableOf} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {MainEventPage} from "./dto/mainEventPage";
import {Event, EventListResult} from "./dto/event";
import {ResponseEntity} from "./dto/ResponseEntity";

const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable()
export class EventsService {
  private eventsMainPageUrl = '/api/page/main';
  private eventsRestAdminUrl = '/api/admin/events';
  private eventsRestUrl = '/api/events';
  private eventsDiscoUrl = '/api/events/disco';

  constructor(private http: HttpClient) {
  }

  getMainPageEvents(): Observable<MainEventPage> {
    return this.http.get<MainEventPage>(this.eventsMainPageUrl)
      .pipe(
        tap(mainEventsPage => this.log(`fetched events to Main Page`)),
        catchError(this.handleError('geEvents', new MainEventPage()))
      );
  }

  getEvents(sort: string, order: string, page: number, size: number, filter: string): Observable<EventListResult> {
    const requestUrl = this.eventsRestAdminUrl + `?sort=${sort}&order=${order}&page=${page}&size=${size}&filter=${filter}`;
    return this.http.get<EventListResult>(requestUrl);
  }

  save(event: Event): Observable<Event> {
    return this.http.post<Event>(this.eventsRestAdminUrl, JSON.stringify(event), httpOptions)
      .pipe(
        catchError(this.handleError('saveEvent', new Event()))
      );
  }

  getEvent(eventId: number): Observable<Event> {
    let url = this.eventsRestUrl + '?id=' + eventId;
    return this.http.get<Event>(url)
      .pipe();
  }

  getDiscoEvents(): Observable<EventListResult> {
    const requestUrl = this.eventsDiscoUrl;
    return this.http.get<EventListResult>(requestUrl);
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error); // log to console instead
      alert(operation + ' fail');
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return observableOf(result as T);
    };
  }

  private log(message: string) {
    console.log(message);
  }

  deleteEvent(eventId: number) {
    const url = this.eventsRestAdminUrl + '?id=' + eventId;
    return this.http.delete<ResponseEntity>(url, httpOptions).pipe(
      catchError(this.handleError('deleteEvent', new ResponseEntity()))
    );
  }
}
