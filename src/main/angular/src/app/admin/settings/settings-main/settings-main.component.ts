import {Component, OnInit} from '@angular/core';
import {SettingsSiteDto} from "../../../dto/settingsSiteDto";
import {SettingsService} from "../../../services/settings/settings.service";

@Component({
  selector: 'app-settings-main',
  templateUrl: './settings-main.component.html',
  styleUrls: ['./settings-main.component.css']
})
export class SettingsMainComponent implements OnInit {

  public settingsSiteDto: SettingsSiteDto;

  constructor(private settingsService: SettingsService) {
    this.settingsSiteDto = new SettingsSiteDto();
  }

  async ngOnInit() {
    this.settingsSiteDto = await this.settingsService.getSettingsDto();
    console.log("SettingsScreen ngOnInit");
    console.log(this.settingsSiteDto);
  }

  submitted = true;
  bannerActives = ['true' , 'false'];

  onSubmit() {
    this.settingsService.save(this.settingsSiteDto)
      .pipe()
      .subscribe(value => {
        this.ngOnInit();
        this.submitted = true;
      });
  }

  printError(heroForm) {
    debugger;
    console.log(heroForm.form);
  }
}
