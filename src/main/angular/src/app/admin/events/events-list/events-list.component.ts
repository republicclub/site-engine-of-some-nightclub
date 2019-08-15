import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import {EventsService} from "../../../events.service";
import {EventDto} from "../../../dto/eventDto";
import {MatPaginator, MatSort, MatTableDataSource} from "@angular/material";
import {catchError, map, startWith, switchMap} from "rxjs/operators";
import {merge, of as observableOf} from 'rxjs';

@Component({
  selector: 'app-events-list',
  templateUrl: './events-list.component.html',
  styleUrls: ['./events-list.component.css']
})
export class EventsListComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'startEvent'];
  isLoadingResults = true;
  isRateLimitReached = false;


  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  events: EventDto[] = [];

  @Output() onRowSelected = new EventEmitter();
  private dataSource: MatTableDataSource<EventDto>;
  private actual: boolean;

  constructor(private eventsService: EventsService) {
  }

  ngOnInit() {
    this.dataSource = new MatTableDataSource(this.events);
    this.dataSource.sort = this.sort;
    this.dataSource.sort.active = 'startEvent';
    this.sort.direction = 'desc';
    const pageSize = +localStorage.getItem('eventListPaginatorPageSize');
    this.paginator.pageSize = (pageSize > 0) ? pageSize : 10;

    // If the user changes the sort order, reset back to the first page.
    this.sort.sortChange.subscribe(() => this.paginator.pageIndex = 0);
    this.actual = Boolean(localStorage.getItem('actual'));
    this.loadData();
  }


  private loadData() {
    merge(this.sort.sortChange, this.paginator.page)
      .pipe(
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          localStorage.setItem('eventListPaginatorPageSize', String(this.paginator.pageSize))
          return this.eventsService!.getEvents(
            this.sort.active,
            this.sort.direction,
            this.paginator.pageIndex,
            this.paginator.pageSize,
            this.dataSource.filter,
            this.actual);
        }),
        map(data => {
          // Flip flag to show that loading has finished.
          this.isLoadingResults = false;
          this.isRateLimitReached = false;
          this.paginator.length = data.total;
          return data.items;
        }),
        catchError(() => {
          this.isLoadingResults = false;
          // Catch if the GitHub API has reached its rate limit. Return empty data.
          this.isRateLimitReached = true;
          return observableOf([]);
        })
      ).subscribe(data => this.events = data);
  }

  handleRowClick(row: EventDto) {
    this.onRowSelected.emit(row);
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
    this.loadData();
  }

  actualChange() {
    localStorage.setItem('actual', String(this.actual));
    this.loadData();
  }
}
