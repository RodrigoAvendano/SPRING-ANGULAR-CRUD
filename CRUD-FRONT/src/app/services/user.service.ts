import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import baserUrl from './helper';

@Injectable({
  providedIn: 'root'
})

export class UserService {
  /* El constructor recibe un parámetro de tipo HttpClient */
  constructor(private httpClient : HttpClient) { }

  /*  */
  public añadirUsuario(user : any) {
    /*Petición post a la dirección localhost almacenada en baseUrl enviando un usuario*/
    return this.httpClient.post(`${baserUrl}/usuarios`, user);
  }
}
