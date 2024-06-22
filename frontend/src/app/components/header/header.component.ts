import { Component, OnInit } from '@angular/core';
import { User } from '../../common/user';
import { UserService } from '../../services/user.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  currentUser: User | null = null;
  isOpen: boolean = false;

  constructor(private userService: UserService,
              private toastService: ToastrService,
              private route: Router) { }

  ngOnInit(): void {
    this.userService.currentUser$.subscribe(response => {
      this.currentUser = response;
    })
  }

  handleDropdown() {
    this.isOpen = !this.isOpen;
  }

  logout() {
    this.isOpen = false;
    this.userService.clearCurrentUser();
    this.showSuccess();
    this.route.navigate(['/posts']);
  }

  showSuccess() {
    this.toastService.info('You have succesfully logout');
  }
}
