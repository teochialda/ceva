import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { UserService } from './services/user-service';
import { HomeComponent } from './components/home/home.component';
import { DialogComponent } from './components/dialog/dialog.component';
import { MealPlanComponent } from './components/meal-plan/meal-plan.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { MealPlanGainComponent } from './components/meal-plan-gain/meal-plan-gain.component';
import { MealPlanMantainComponent } from './components/meal-plan-mantain/meal-plan-mantain.component';
import { EctomorphComponent } from './components/ectomorph/ectomorph.component';
import { EndomoprhComponent } from './components/endomoprh/endomoprh.component';
import { MesomorphComponent } from './components/mesomorph/mesomorph.component';
import { RecipeComponent } from './components/recipe/recipe.component';
import { NgxYoutubePlayerModule } from 'ngx-youtube-player';
import { WorkoutsComponent } from './components/workouts/workouts.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MediaPlayerComponent } from './components/media-player/media-player.component';
import { MatButtonModule } from '@angular/material/button';
import { MatDialogModule } from '@angular/material/dialog';

@NgModule({
  declarations: [
    AppComponent,
    RegisterComponent,
    LoginComponent,
    HomeComponent,
    DialogComponent,
    MealPlanComponent,
    MealPlanGainComponent,
    MealPlanMantainComponent,
    EctomorphComponent,
    EndomoprhComponent,
    MesomorphComponent,
    RecipeComponent,
    WorkoutsComponent,
    MediaPlayerComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgbModule,
    NgxYoutubePlayerModule.forRoot(),
    BrowserAnimationsModule,
    MatButtonModule,
    MatDialogModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent]
})
export class AppModule { }
