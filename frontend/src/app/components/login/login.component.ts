import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SecurityService } from '../../services/security.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  loginFormGroup!: FormGroup;

  constructor(private formBuilder: FormBuilder,
              private securityService: SecurityService) { }

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        username: [''],
        password: ['']
      })
    })
  }

  onSubmit() {
    const userData = this.loginFormGroup.get('user')?.value;
    this.securityService.performLogin(userData).subscribe(response => {
      console.log(response);
    });
  }
}
