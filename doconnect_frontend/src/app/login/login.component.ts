import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  constructor( private userService:UserService
    , private router:Router) { }

  ngOnInit(): void {
  }

 user=new User()
  email:string=''
  password:string=''

  sendUserData(user:User){
    this.userService.sendUserData(user)
  }

 userLogin(data:any) {

  this.email=data.email
  this.password=data.password

	this.userService.userLogin(this.email,this.password).
  subscribe((data)=>{
    this.user=data
    this.sendUserData(this.user)
    this.router.navigate(['/user'])
  },err =>{
    alert("UserName or Password Wrong")
  })
	}
}
