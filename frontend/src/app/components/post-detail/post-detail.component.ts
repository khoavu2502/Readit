import { Component, OnInit, ViewEncapsulation, ɵ_sanitizeHtml } from '@angular/core';
import { PostService } from '../../services/post.service';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../../common/post';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.css'
})
export class PostDetailComponent implements OnInit {

  post!: Post;
  cleanContent!: SafeHtml;

  constructor(private postService: PostService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.handlePostDetails();
    })
  }

  handlePostDetails() {
    const postId = +this.route.snapshot.paramMap.get("id")!;
  
    this.postService.getPost(postId).subscribe(response => {
      this.post = response;
      this.cleanContent = this.sanitizer.bypassSecurityTrustHtml(this.post.content);
    });
  }
}
