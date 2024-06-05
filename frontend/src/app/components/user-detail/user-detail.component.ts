import { Component, OnInit } from '@angular/core';
import { User } from '../../common/user';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom, forkJoin, throwError } from 'rxjs';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css'
})
export class UserDetailComponent implements OnInit {
  user!: User| null;
  currentLoggedIn!: User | null;
  isFollowing!: boolean;

  constructor(private userService: UserService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.loadData();
  }

  async loadData() {
    try {
      // load following user
      await this.getUser();

      // load current user
      this.currentLoggedIn = await firstValueFrom(this.userService.currentUser$);

      // set following status
      this.checkIfFollowing();
    } catch (err) {
      console.log(err);
    }
  }

  async getUser() {
    const followingId = +this.route.snapshot.paramMap.get('id')!;

    this.user = await firstValueFrom(this.userService.getUser(followingId));
  }

  checkIfFollowing() {
    if (this.user && this.currentLoggedIn) {
      this.isFollowing = this.currentLoggedIn.following.includes(this.user.id);
    } else {
      console.error();
    }
  }

  interact() {
    if (this.isFollowing) {
      this.isFollowing = false;
      this.userService.unFollow(this.currentLoggedIn?.id, this.user?.id).subscribe((response) => {
        this.updateUsers(response);
      });
    } else {
      this.isFollowing = true;
      this.userService.follow(this.currentLoggedIn?.id, this.user?.id).subscribe((response) => {
        this.updateUsers(response);
      });
    }


  }

  updateUsers(response: (User | null)[]) {
    this.userService.setCurrentUser(response[0]);
    this.user = response[1];
  }
}