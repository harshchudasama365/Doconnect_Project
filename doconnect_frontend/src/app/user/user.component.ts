import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Answer } from './../answer';
import { Question } from './../question';
import { User } from '../user';
import { Router } from '@angular/router';


@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.css']
})
export class UserComponent implements OnInit {

  constructor(private userService:UserService , private router:Router) {

   }

  ngOnInit(): void {
    this.user=this.userService.giveUserData()
    console.log("user data is"+this.user.id)
    if(this.user.id==0){
      alert("Login required")
      this.router.navigate(['/login'])
    }

  }

  answer:Answer | undefined
  answers:Answer[] | undefined
  questions:Question[] | undefined
  response:any
  user= new User()



	searchQuestion(question:string) {
	   this.userService.searchQuestion(question).subscribe((data)=>{
      this.questions=data
     })
	}

  userLogout( userId:number) {
		 this.userService.userLogout(userId).subscribe((data)=>{
      this.response=data
     },err =>{
      this.user=new User()
      this.userService.sendUserData(this.user)
      this.router.navigate(["/login"])
     }
     )
	}

}
