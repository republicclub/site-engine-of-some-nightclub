import {Component, OnInit} from '@angular/core';
import {EventDto} from "../../../dto/eventDto";
import {SettingsSiteDto} from "../../../dto/settingsSiteDto";
import {EventsService} from "../../../events.service";
import {SettingsService} from "../../../services/settings/settings.service";

@Component({
  selector: 'app-settings-main',
  templateUrl: './settings-main.component.html',
  styleUrls: ['./settings-main.component.css']
})
export class SettingsMainComponent implements OnInit {

  public settingsSiteDto: SettingsSiteDto;
  events: EventDto[];

  constructor(private settingsService: SettingsService, private eventsService : EventsService) {
    this.settingsSiteDto = new SettingsSiteDto();
  }

  async ngOnInit() {
    this.settingsSiteDto = await this.settingsService.getSettingsDto();
    this.eventsService.getEvents('startEvent','desc',0,100,'',true)
      .pipe()
      .subscribe(result=> {
        this.events = result.items;
      })

  }

  submitted = true;
  onSubmit() {
    this.settingsService.save(this.settingsSiteDto)
      .pipe()
      .subscribe(value => {
        this.ngOnInit();
        this.submitted = true;
      });
  }
}
