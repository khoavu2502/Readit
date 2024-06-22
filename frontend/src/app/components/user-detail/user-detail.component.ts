import { Component, OnInit } from '@angular/core';
import { User } from '../../common/user';
import { UserService } from '../../services/user.service';
import { ActivatedRoute } from '@angular/router';
import { firstValueFrom, forkJoin, throwError } from 'rxjs';
import { Post } from '../../common/post';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-user-detail',
  templateUrl: './user-detail.component.html',
  styleUrl: './user-detail.component.css'
})
export class UserDetailComponent implements OnInit {
  user!: User| null;
  currentLoggedIn!: User | null;
  isFollowing!: boolean;
  posts!: any;

  constructor(private userService: UserService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.loadData();
    })
  }

  async loadData() {
    try {
      // load following user
      await this.getUser();

      // load current user
      this.currentLoggedIn = await firstValueFrom(this.userService.currentUser$);

      // set following status
      this.checkIfFollowing();

      // load user's posts
      if (this.user) this.loadPosts(this.user.id);
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
      console.log(this.isFollowing);
    } else {
      console.error();
    }
  }

  loadPosts(userId: number) {
    this.userService.loadPosts(userId).subscribe(posts => {
      this.posts = posts.map(post => {
        return { ...post, content: this.sanitizer.bypassSecurityTrustHtml(this.extractContentWithoutImages(post.content))}
      })
    })
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

  extractContentWithoutImages(html: string): string {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');
    const imageTags = doc.querySelectorAll('img');
    imageTags.forEach(img => img.remove());
    return doc.body.innerHTML;
  }
}
