import { Component, OnInit } from '@angular/core';
import { User } from '../../common/user';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  currentUser: User | null = null;
  isOpen: boolean = false;

  constructor(private userService: UserService) { }

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
  }
}
