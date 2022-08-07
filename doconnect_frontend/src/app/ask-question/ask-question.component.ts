import { Component, OnInit } from '@angular/core';
import { UserService } from './../user.service';
import { Question } from './../question';
import { User } from '../user';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-ask-question',
  templateUrl: './ask-question.component.html',
  styleUrls: ['./ask-question.component.css']
})
export class AskQuestionComponent implements OnInit {

  constructor(private userService:UserService , private router:Router
    ,private httpClient: HttpClient) { }



  selectedFile: File;
  retrievedImage: any;
  base64Data: any;
  retrieveResonse: any;
  message: string
  imageName: any;

  ngOnInit(): void {
    this.user=this.userService.giveUserData()
    console.log(this.user.id)
    if (this.user.id==0) {
      alert("Login required")
      this.router.navigate(['/login'])
    }
  }
askQuestionDTO={
  userId:0,
  question:'',
  topic:''
}

user=new User()

question: Question | undefined
  askQuestion(data:any) {
    this.askQuestionDTO.question=data.question
    this.askQuestionDTO.userId=this.user.id
    this.askQuestionDTO.topic=data.topic
    this.userService.askQuestion(( this.askQuestionDTO)).subscribe((data)=>{
      this.question=data
      alert("Question Posted ")
      this.router.navigate(['/user'])
    })

    }


    public onFileChanged(event) {
      //Select File
      this.selectedFile = event.target.files[0];
    }


    //Gets called when the user clicks on submit to upload the image
    onUpload() {
      console.log(this.selectedFile);

      //FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
      const uploadImageData = new FormData();
      uploadImageData.append('imageFile', this.selectedFile, this.selectedFile.name);

      //Make a call to the Spring Boot Application to save the image
      this.httpClient.post('http://localhost:9090/user/upload', uploadImageData, { observe: 'response' })
        .subscribe((response) => {
          if (response.status === 200) {
            this.message = 'Image uploaded successfully';
            alert("Uploaded")
          } else {
            this.message = 'Image not uploaded successfully';
          }
        }
        );

    }

      //Gets called when the user clicks on retieve image button to get the image from back end
      getImage() {
      //Make a call to Sprinf Boot to get the Image Bytes.
      this.httpClient.get('http://localhost:9090/user/get/' + this.imageName)
        .subscribe(
          res => {
            this.retrieveResonse = res;
            this.base64Data = this.retrieveResonse.picByte;
            this.retrievedImage = 'data:image/jpeg;base64,' + this.base64Data;
          }
        );
    }

}


