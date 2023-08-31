import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  loginData = {
    "username": '',
    "password": ''
  }
  constructor(
    private snack : MatSnackBar,
    private loginService : LoginService,
    private router : Router
    ) {}

  ngOnInit(): void {}

  formSubmit() {
    if(this.loginData.username.trim() == '' || this.loginData.username.trim() == null){
      this.snack.open('El nombre de usuario es requerido','Aceptar',{
        duration:3000
      })
    }

    if(this.loginData.password.trim() == '' || this.loginData.password.trim() == null){
      this.snack.open('La contraseña es requerida','Aceptar',{
        duration:3000
      })
    }

    /* Recibe los datos de la petición post */
    this.loginService.generateToken(this.loginData).subscribe(
      (data) => {
        this.router.navigate(['/signup']);
      }, (error) => {
        this.snack.open('Usuario/Contraseña incorrecto','Aceptar',{
          duration:3000
        })
      }
    );
  }

}
