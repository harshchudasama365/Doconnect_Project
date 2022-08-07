import { Component, OnInit } from '@angular/core';
import { AdminService } from './../admin.service';

import { Router } from '@angular/router';
import { Admin } from '../admin';


@Component({
  selector: 'app-admin-login',
  templateUrl: './admin-login.component.html',
  styleUrls: ['./admin-login.component.css']
})
export class AdminLoginComponent implements OnInit {

  constructor(private adminService:AdminService, private router:Router) { }

  ngOnInit(): void {
  }

  admin= new Admin()

email:string=''
password:string=''

sendAdminData(admin:Admin){
  this.adminService.sendAdminData(admin)
}
  adminLogin(data:any){
    this.email=data.email;
   this.password=data.password;

     this.adminService.adminLogin(this.email,this.password).subscribe((data)=>{
      this.admin=data
          this.sendAdminData(this.admin)
          this.router.navigate(['/admin'])
     },err =>{
      alert("UserName or Password Wrong")
      }
     )




   }

}
