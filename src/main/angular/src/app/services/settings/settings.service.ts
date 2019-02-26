import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {KeyValueDto} from "../../dto/keyValue";
import {SettingsSiteDto} from "../../dto/settingsSiteDto";


@Injectable()
export class SettingsService {

  constructor(private http: HttpClient) {
  }

  settingsSiteDto: SettingsSiteDto = new SettingsSiteDto();

  async getSettings(): Promise<SettingsSiteDto> {
    const settings = await this.http.get<KeyValueDto[]>('/api/settings').toPromise();
    for (let settingKey in settings) {
      let settingDto = settings[settingKey];
      if (!!settingKey) {
        this.settingsSiteDto[settingDto.key] = settingDto.value;
        if (settingDto.value == "false") {
          this.settingsSiteDto[settingDto.key] = false;
        }
      }
    }
    return this.settingsSiteDto;
  }


  async getSettingsDto(): Promise<SettingsSiteDto> {
    let dto;
    if (!this.settingsSiteDto.riderLight) {
      dto = await this.getSettings()
    } else {
      dto = this.settingsSiteDto;
    }
    return dto
  }

  save(data: SettingsSiteDto) {
    return this.http.post<KeyValueDto[]>(`/api/settings`, data);
  }
}
