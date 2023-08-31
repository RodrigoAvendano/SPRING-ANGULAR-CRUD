import { HttpClient, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import baserUrl from './helper';
import { map, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private http:HttpClient) { }

  /* Genera el token desde Spring */
  public generateToken(loginData:any) {
    /*Ruta, requestBody*/
    return this.http.post(`${baserUrl}/login`,loginData,{
      observe : 'response'
    }).pipe(map((response : HttpResponse<any>) => {
      const body = response.body;
      const bearerToken = body.token;
      localStorage.setItem('token', bearerToken);
      return body;
    }));
  }

  getToken() {
    return localStorage.getItem('token');
  }
}
