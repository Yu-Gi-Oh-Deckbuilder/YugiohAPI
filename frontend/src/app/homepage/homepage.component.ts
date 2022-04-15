import { Component } from '@angular/core';
import { FormBuilder,Validators } from '@angular/forms';
import { UserService } from '../service/user/user.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {

  loginForm = this.formBuilder.group({
    username : ['',Validators.required],
    firstName:['',Validators.required],
    lastName:['',Validators.required],
    email:['',Validators.required],
    password : ['',Validators.required]
  })

  constructor(private formBuilder: FormBuilder,private userService:UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){

    console.warn(this.loginForm.value);
    this.loginForm.value.userRole={
      id:1,
      name:"user"
    }
    console.warn(this.loginForm.value);
    this.userService.signup(this.loginForm.value)
    .subscribe(res=>{
      console.log(res);
    })
  }
}
