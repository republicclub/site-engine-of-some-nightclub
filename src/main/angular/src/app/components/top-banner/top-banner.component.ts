import { Component, OnInit } from '@angular/core';
import {SettingsSiteDto} from "../../dto/settingsSiteDto";
import {SettingsService} from "../../services/settings/settings.service";

@Component({
  selector: 'app-top-banner',
  templateUrl: './top-banner.component.html',
  styleUrls: ['./top-banner.component.css']
})
export class TopBannerComponent implements OnInit {
  settingsBanner: SettingsSiteDto = new SettingsSiteDto();

  constructor(private settingsService: SettingsService) { }

  ngOnInit() {
    this.settingsService.getSettingsDto().then(value => {
      this.settingsBanner = value;
    });
  }

}
