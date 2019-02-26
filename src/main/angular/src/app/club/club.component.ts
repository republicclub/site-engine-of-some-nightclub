import {Component, OnInit} from '@angular/core';
import {SettingsService} from "../services/settings/settings.service";
import {SettingsSiteDto} from "../dto/settingsSiteDto";

@Component({
  selector: 'app-club',
  templateUrl: './club.component.html',
  styleUrls: ['./club.component.css']
})
export class ClubComponent implements OnInit {

  settingsDto : SettingsSiteDto = new SettingsSiteDto();

  constructor(private settingsService: SettingsService) {
  }

  async ngOnInit() {
    this.settingsDto = await this.settingsService.getSettingsDto();
  }

}
