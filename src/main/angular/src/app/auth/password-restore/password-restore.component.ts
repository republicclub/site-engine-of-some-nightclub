import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AuthService} from "../../auth.service";

@Component({
  selector: 'app-password-restore',
  templateUrl: './password-restore.component.html',
  styleUrls: ['./password-restore.component.css']
})
export class PasswordRestoreComponent implements OnInit {
  passwordNotMatch: boolean = false;
  password : string;
  password2 : string;
  token : string;
  tokenValid: boolean;
  username: string;
  message: string;
  messagePositive: boolean;
  passwordChangeFormVisible: boolean;


  constructor(private activatedRoute: ActivatedRoute, private authService : AuthService) { }

  ngOnInit() {
    this.token = this.activatedRoute.snapshot.paramMap.get('token');
    this.authService.getUserByResetToken(this.token)
      .pipe()
      .subscribe(response => {
        if (response.success) {
          this.tokenValid = true;
          this.username = response.data;
          this.passwordChangeFormVisible = true;
        } else {
          this.tokenValid = false;
          this.message = response.message;
          this.messagePositive = false;
        }
      });
  }

  changePassword() {
    let passwordEquals = (this.password == this.password2);
    this.passwordNotMatch = !passwordEquals;
    if (passwordEquals) {
      this.authService.updatePassword(this.token, this.password)
        .pipe()
        .subscribe(response => {
          if (response.success) {
            this.message  = response.data;
          } else {
            this.message = response.message;
          }
          this.messagePositive = response.success;
          this.passwordChangeFormVisible = false;
        })
    }
  }
}
