import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { HttpClientModule, provideHttpClient, withInterceptors } from '@angular/common/http';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { ReactiveFormsModule } from '@angular/forms';
import { authInterceptor } from './interceptors/auth.interceptor';
import { loaderInterceptor } from './interceptors/loader.interceptor';
import { LoaderComponent } from './components/loader/loader.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { UserDetailComponent } from './components/user-detail/user-detail.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    LandingPageComponent,
    PostListComponent,
    PostDetailComponent,
    LoginComponent,
    RegistrationComponent,
    LoaderComponent,
    UserDetailComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      timeOut: 3000,
      positionClass: 'toast-top-left',
      preventDuplicates: true,
      easing: 'ease-in',
      easeTime: 300,

    }),
  ],
  providers: [
    provideHttpClient(withInterceptors([authInterceptor, loaderInterceptor]))
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
