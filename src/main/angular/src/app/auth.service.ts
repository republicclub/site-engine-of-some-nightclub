import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs/index";
import {UserDto} from "./dto/userDto";
import {ResponseEntity} from "./dto/ResponseEntity";
import {httpOptions} from "./dto/httpOptions";

@Injectable()
export class AuthService {

  constructor(private http: HttpClient) {
  }

  login(username: string, password: string): Observable<ResponseEntity> {
    let url = '/api/loginprocessing?username=' + username + '&password=' + password;
    return this.http.post<ResponseEntity>(url, {}, httpOptions).pipe(
      // catchError(this.handleError('authService.login', new ResponseEntity()))
    );
  }

  currentUser(): Observable<UserDto> {
    return this.http.get<UserDto>('/api/user').pipe();
  }

  singin(user: UserDto) {
    let url = '/api/user/singin';
    return this.http.post<ResponseEntity>(url, user, httpOptions);
  }

  sendNewPassword(usernameOrEmail: string) {
    let url = '/api/user/password/token/request?usernameOrEmail=' + usernameOrEmail;
    return this.http.post<ResponseEntity>(url, {}, httpOptions)
  }

  getUserByResetToken(token: string) {
    let url = '/api/user/password/token/check?token=' + token;
    return this.http.post<ResponseEntity>(url, {}, httpOptions)
  }

  updatePassword(token: string, password: string) {
    let url = '/api/user/password/token/update?token=' + token + '&password=' + password;
    return this.http.post<ResponseEntity>(url, {}, httpOptions)
  }
}
