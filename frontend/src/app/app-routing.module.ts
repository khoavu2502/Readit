import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LandingPageComponent } from './components/landing-page/landing-page.component';
import { PostListComponent } from './components/post-list/post-list.component';
import { PostDetailComponent } from './components/post-detail/post-detail.component';
import { LoginComponent } from './components/login/login.component';
import { RegistrationComponent } from './components/registration/registration.component';
import { UserDetailComponent } from './components/user-detail/user-detail.component';

const routes: Routes = [
  { path: '', component: LandingPageComponent },
  { path: 'posts', component: PostListComponent },
  { path: 'posts/:id', component: PostDetailComponent },
  { path: 'login', component: LoginComponent },
  { path: 'signup', component: RegistrationComponent },
  { path: 'users/:id', component: UserDetailComponent },
  {path: '**', redirectTo: '/posts', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes,
                                { scrollPositionRestoration: 'enabled',
                                  onSameUrlNavigation: 'reload'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
