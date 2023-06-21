import { Component } from '@angular/core';
import { MediaPlayerComponent } from '../media-player/media-player.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-workouts',
  templateUrl: './workouts.component.html',
  styleUrls: ['./workouts.component.css']
})
export class WorkoutsComponent {
  constructor(private dialog: MatDialog) {}

  openMediaPlayer(videoId: string): void {
    const dialogRef = this.dialog.open(MediaPlayerComponent, {
      width: '800px',
      height: '600px',
      data: { videoId }
    });
  }
}
