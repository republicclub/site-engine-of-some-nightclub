import {Component, OnInit} from '@angular/core';
import {EventReport} from "../../../dto/eventGallery";
import {EventReportService} from "../../../event-report.service";
import * as moment from "moment";
import {PreSummaryWrapper} from "../../../dto/preSummaryWrapper";

@Component({
  selector: 'app-pre-summary',
  templateUrl: './pre-summary.component.html',
  styleUrls: ['./pre-summary.component.css']
})
export class PreSummaryComponent implements OnInit {
  dataGallery: PreSummaryWrapper = new PreSummaryWrapper();
  private galleryView: boolean = true;
  wait: boolean = true;

  constructor(private service: EventReportService) {
  }

  ngOnInit() {
    this.galleryView = true;
    this.dataGallery.report = new EventReport();
    this.service.getReportEvents().subscribe(eventsReport => {
      this.dataGallery.reports = eventsReport;
      this.dataGallery.reports.forEach(eventsReport => {
        eventsReport.startEvent = moment(eventsReport.startEvent).format('D MMM YYYY');
        eventsReport.name = eventsReport.name.substring(0, 12);
      })
      this.wait = false;
    });
  }

  addNewPhotoReport() {
    this.galleryView = false;
    this.dataGallery.report = new EventReport();
    this.dataGallery.reports = [];
  }

  onEditReportClick() {
    this.galleryView = false;
  }
}
