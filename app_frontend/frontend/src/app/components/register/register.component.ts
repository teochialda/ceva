import { Component, OnInit } from '@angular/core';
import { FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { UserDTO } from 'src/app/models/user-dto';
import { UserService } from 'src/app/services/user-service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {

  user: UserDTO = {
    id: 0,
    name: '',
    password: '',
    age: 0,
    kilograms: 0
  }

  constructor(private userService: UserService, private router: Router) {}

  addUser(): void {
    this.userService.insertUser(this.user).subscribe(

      (response) => {
        console.log('User registered successfully:', response);
        console.log(this.user);

        const useridstring = response.id.toString();
        localStorage.setItem('userId', useridstring);
        this.router.navigate(['/']);
      },
      (error) => {
        console.error('Failed to register user:', error);
        // Optionally, handle the error response here
        console.log(this.user);
      }
    )
  }

}
