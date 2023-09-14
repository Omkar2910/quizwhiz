import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms'; // Import necessary modules
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  signupForm: FormGroup; // Define a FormGroup

  constructor(private userService: UserService, private fb: FormBuilder) {
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
        next: (data) => {
          console.log(data);
          alert('Success');
        },
        error: (error) => {
          console.error(error);
          alert('Something went wrong');
        },
      });
    } else {
      alert('Please fill in the required fields correctly.');
    }
  }
}
