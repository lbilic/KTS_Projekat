import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomeComponent } from "./home/home.component";
import { LoginComponent } from './login/login.component';
import { RegistrationComponent } from './registration/registration.component';
import {TimetableComponent} from './timetable/timetable.component';
import {ShowCurrentPricelistDialogComponent} from './pricelist/show-current-pricelist/show-current-pricelist-dialog.component';
import { AddPricelistDialogComponent } from './pricelist/add-pricelist-dialog/add-pricelist-dialog.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegistrationComponent },
  { path: 'timetable', component: TimetableComponent },
  { path: 'addpricelist', component: AddPricelistDialogComponent},
  { path: 'showpricelist', component: ShowCurrentPricelistDialogComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
