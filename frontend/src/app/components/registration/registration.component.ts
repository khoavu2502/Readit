import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { SecurityService } from '../../services/security.service';
import { UserService } from '../../services/user.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrl: './registration.component.css',
})
export class RegistrationComponent implements OnInit {

  registrationFormGroup!: FormGroup;
  isVisible: boolean = false;

  constructor(private formBuilder: FormBuilder,
              private securityService: SecurityService,
              private userService: UserService,
              private router: Router,
              private toastr: ToastrService) { }

  ngOnInit(): void {
    this.registrationFormGroup = this.formBuilder.group({
      user: this.formBuilder.group({
        username: [''],
        email: [''],
        password: [''],
        passwordConfirmation: ['']
      })
    })
  }

  onSubmit() {

    // require log out before register in the future
    this.userService.clearCurrentUser();

    const userData = this.registrationFormGroup.get('user')?.value;

    this.securityService.performSignup(userData).subscribe({
      next: (data) => {
        this.showSuccess();
        this.router.navigate(['/login']);
      },

      error: (errors) => {
        this.setErrors(errors.error);
      }
    });
  }

  setErrors(errors: any) {
    for (let key in errors) {
      if (key === 'userDto') {
        this.registrationFormGroup.get('user.passwordConfirmation')?.setErrors({ 'error': errors[key] });
      } else {
        this.registrationFormGroup.get(`user.${key}`)?.setErrors({ 'error': errors[key] });
      }
    }
  }

  showSuccess() {
    this.toastr.success('You have succesfully sign up');
  }

  handleVisibility() {
    this.isVisible = !this.isVisible;
  }
}
