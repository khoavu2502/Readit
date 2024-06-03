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
  hasLoginError: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private securityService: SecurityService) { }

  ngOnInit(): void {
    this.loginFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        username: [''],
        password: ['']
      })
    })

    this.securityService.isError$.subscribe(state => {
      this.hasLoginError = state;
    })
  }

  onSubmit() {
    const userData = this.loginFormGroup.get('user')?.value;

    this.securityService.loginAndSetCurrentUser(userData);
  }
}
