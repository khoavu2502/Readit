import { Component, OnInit } from '@angular/core';
import { PostService } from '../../services/post.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Post } from '../../common/post';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CommentService } from '../../services/comment.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-post-detail',
  templateUrl: './post-detail.component.html',
  styleUrl: './post-detail.component.css'
})
export class PostDetailComponent implements OnInit {

  post!: Post;
  cleanContent!: SafeHtml;
  commentForm!: FormGroup;
  isDisplay: boolean = false;

  constructor(private postService: PostService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer,
              private formBuilder: FormBuilder,
              private commentService: CommentService,
              private toastr: ToastrService,
              private navigate: Router) { }

  ngOnInit(): void {
    this.commentForm = this.formBuilder.group({
      content: [''],
      user: this.setUser(),
      post: ['']
    })

    this.route.paramMap.subscribe(() => {
      this.handlePostDetails();
    })
  }

  handlePostDetails() {
    const postId = +this.route.snapshot.paramMap.get("id")!;
  
    this.postService.getPost(postId).subscribe(response => {
      this.post = response;
      this.commentForm.patchValue({
        post: this.post
      })
      this.cleanContent = this.sanitizer.bypassSecurityTrustHtml(this.post.content);
    });
  }

  addEmoji(event: any) {
    const content = this.commentForm.get('content')?.value;

    this.commentForm.get('content')?.setValue(content + event.emoji.native);
  }

  onSubmit(value: any) {
    this.commentService.createComment(value).subscribe({
      next: (data) => {
        window.location.reload();
      },
      error: (errors) => {
        this.setErrors(errors.error);
      }
    })
  }

  setUser() {
    // require login to create post in the future to ensure that currentUser not null
    const currentUser = localStorage.getItem('currentUser');
    return currentUser ? JSON.parse(currentUser) : null;
  }

  toggleDisplay() {
    this.isDisplay = !this.isDisplay;
  }

  setErrors(errors: any) {
    for (let key in errors) {
      console.log(`${key}: ${errors[key]}`)
      this.commentForm.get(`${key}`)?.setErrors({ 'error': errors[key] });
    }
  }
}
