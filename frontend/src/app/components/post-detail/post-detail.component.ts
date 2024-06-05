import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../../common/post';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.css'
})
export class PostDetailComponent implements OnInit {

  post!: Post;

  constructor(private postService: PostService,
              private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handlePostDetails();
    })
  }

  handlePostDetails() {
    const postId = +this.route.snapshot.paramMap.get("id")!;
  
    this.postService.getPost(postId).subscribe(response => {
      this.post = response;
    });
  }
}
