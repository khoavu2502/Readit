import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../../common/post';
import { PostService } from '../../services/post.service';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {

  posts: Post[] = [];

  constructor(private postService: PostService,
              private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listPosts();
    });
  }

  listPosts() {
    this.postService.getPostList().subscribe(response => {
      this.posts = response;
      console.log(this.posts);
    })
  }
}
