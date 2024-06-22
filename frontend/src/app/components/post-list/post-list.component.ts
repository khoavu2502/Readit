import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../../common/post';
import { PostService } from '../../services/post.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-post-list',
  templateUrl: './post-list.component.html',
  styleUrl: './post-list.component.css'
})
export class PostListComponent implements OnInit {

  posts!: any;

  constructor(private postService: PostService,
              private sanitizer: DomSanitizer,
              private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listPosts();
    });
  }

  listPosts() {
    this.postService.getPostList().subscribe(response => {
      this.posts = response.map(post => {
        return { ...post, content: this.sanitizer.bypassSecurityTrustHtml(this.extractContentWithoutImages(post.content))}
      })
    })
  }

  extractContentWithoutImages(html: string): string {
    const parser = new DOMParser();
    const doc = parser.parseFromString(html, 'text/html');
    const imageTags = doc.querySelectorAll('img');
    imageTags.forEach(img => img.remove());
    return doc.body.innerHTML;
  }
}
