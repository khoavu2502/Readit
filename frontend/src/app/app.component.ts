import { Component, OnInit } from '@angular/core';
import { UserService } from './services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {

  title = 'frontend';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.loadCurrentUser();
  }
}
