import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Import necessary modules
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup;

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private snakBar: MatSnackBar
  ) {
    // Create the form group with validators
    this.signupForm = this.fb.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.pattern('^[0-9]*$')]],
    });
  }

  ngOnInit(): void {}

  formSubmit() {
    if (this.signupForm.valid) {
      // If the form is valid, submit the user object
      this.userService.addUser(this.signupForm.value).subscribe({
        next: (data:any) => {
          console.log(data);
          // show snakbar
          this.openSnakBar('Signup Successful', 'OK');
        },
        error: (error) => {
          console.error(error);
          this.openSnakBar(error.error, 'OK');
        },
      });
    } else {
      this.openSnakBar('Please fill in the required fields correctly.', 'OK');
    }
  }

  openSnakBar(message: string, action: string) {
    this.snakBar.open(message, action, {
      duration: 2000,
      verticalPosition: 'top',
      horizontalPosition: 'center',
    });
  }
}
