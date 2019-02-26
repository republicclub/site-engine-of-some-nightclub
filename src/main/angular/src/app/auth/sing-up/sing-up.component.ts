import { Component, OnInit } from '@angular/core';
import {UserDto} from "../../dto/userDto";
import {AuthService} from "../../auth.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sing-up',
  templateUrl: './sing-up.component.html',
  styleUrls: ['./sing-up.component.css']
})
export class SingUpComponent implements OnInit {

  passwordError: boolean;
  user: UserDto;
  passwordAgain: string;
  regSend: boolean;
  message: string;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.passwordError = false;
    this.user = new UserDto();
    this.regSend = false;
    this.message = '';
  }

  singInClick() {
    this.passwordError = false;
    if (this.passwordAgain != this.user.password) {
      this.passwordError = true;
    } else {
      this.authService.singin(this.user)
        .pipe()
        .subscribe(responce => {
          if (responce.success) {
            this.regSend = true;
          } else {
            this.message = responce.message;
          }
        })
    }
  }
}
