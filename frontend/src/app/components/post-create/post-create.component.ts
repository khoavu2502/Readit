import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Category } from '../../common/category';
import { CategoryService } from '../../services/category.service';
import { PostService } from '../../services/post.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-post-create',
  templateUrl: './post-create.component.html',
  styleUrl: './post-create.component.css'
})
export class PostCreateComponent implements OnInit {

  postForm!: FormGroup;

  categories!: Category[];

  editorConfig = {
    base_url: '/tinymce',
    suffix: '.min',
    plugins: 'lists link image table help wordcount textcolor fontselect fontsizeselect',
    menubar: true,
    toolbar: "undo redo | link image | blocks fontfamily fontsize | bold italic underline forecolor backcolor | align lineheight checklist bullist numlist | indent outdent",
    placeholder: 'Tell a story...',
    menu: {
      file: { title: 'File', items: 'newdocument restoredraft | preview | importword exportpdf exportword | print | deleteallconversations' },
      edit: { title: 'Edit', items: 'undo redo | cut copy paste pastetext | selectall | searchreplace' },
      view: { title: 'View', items: 'code revisionhistory | visualaid visualchars visualblocks | spellchecker | preview fullscreen | showcomments' },
      insert: { title: 'Insert', items: 'image link media addcomment pageembed codesample inserttable | math | charmap emoticons hr | pagebreak nonbreaking anchor tableofcontents | insertdatetime' },
      format: { title: 'Format', items: 'bold italic underline strikethrough superscript subscript codeformat | styles blocks fontfamily fontsize align lineheight | forecolor backcolor | language | removeformat' },
      tools: { title: 'Tools', items: 'spellchecker spellcheckerlanguage | a11ycheck code wordcount' },
      table: { title: 'Table', items: 'inserttable | cell row column | advtablesort | tableprops deletetable' },
      help: { title: 'Help', items: 'help' }
    },
    height: '100vh',
    images_upload_handler: (blobInfo: any, progress: any) => {
      return new Promise<string>((resolve, reject) => {
        this.uploadFileToS3Bucket(blobInfo.blob(), resolve, reject);
      })
    }
  }

  constructor(private formBuilder: FormBuilder,
              private categoryService: CategoryService,
              private postService: PostService,
              private route: Router,
              private toastr: ToastrService) { }

  ngOnInit(): void {

    this.loadCategories();

    this.postForm = this.formBuilder.group({
      title: [''],
      content: [''],
      thumbnail: '/assets/images/crayon-waiting-3_xlr4rh.png',
      category: new FormControl(),
      user: this.loadCurrentUser()
    })
  }

  loadCategories() {
    // get categories from API call
    this.categoryService.loadCategories().subscribe(response => {
      this.categories = response;
    });
  }

  onSubmit(value: any) {
    this.postService.createPost(value).subscribe({
      next: (data) => {
        this.showSuccess();
        this.route.navigate([`/posts/${data.id}`]);
      },
      error: (errors) => {
        this.setErrors(errors.error);
      }
    });
  }

  loadCurrentUser() {
    // require login to create post in the future to ensure that currentUser not null
    const currentUser = localStorage.getItem('currentUser');
    return currentUser ? JSON.parse(currentUser) : null;
  }

  setErrors(errors: any) {
    for (let key in errors) {
      console.log(`${key}: ${errors[key]}`)
      this.postForm.get(`${key}`)?.setErrors({ 'error': errors[key] });
    }
  }

  showSuccess() {
    this.toastr.success("You have successfully created a new post");
  }

  uploadFileToS3Bucket = async (file: any, resolveCallback: any, rejectCallback: any) => {
    
    const self = this;

    let form = new FormData();
  
    form.append("file", file);
  
    // Get CSRFToken from the meta tag
  
    let xhr = new XMLHttpRequest();
    // Send a POST request to the route `/s3-file-uploads`
    // provided in the router and assigned a controller to
    // perform the request
    xhr.open("POST", "http://localhost:8080/api/v1/postImages", true);

    const jwtToken = localStorage.getItem('jwtToken');
    xhr.setRequestHeader('Authorization', 'Bearer ' + jwtToken);
  
    xhr.onerror = function (event) {
      rejectCallback("Unable to upload the file.");
    };
  
    xhr.onload = function () {
      if (xhr.status === 201 || xhr.status === 200) {
        // Get the full path of the uploaded file
        const response = JSON.parse(xhr.responseText);
        const fileUrl = response.location;

        self.postForm.patchValue({
          thumbnail: fileUrl
        })

        resolveCallback(fileUrl);
      } else {
        rejectCallback("Unable to upload the file.");
      }
    };
  
    return xhr.send(form);
  };
}
