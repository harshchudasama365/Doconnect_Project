import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MessageDTO } from './messageDTO';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor( private http:HttpClient) { }

  url:string='http://localhost:9595/chat/'

	sendMessage(messageDTO:any):Observable<MessageDTO> {
		return this.http.post<MessageDTO>("http://localhost:9090/user/sendMessage" ,messageDTO);
	}

	getMessage():Observable<MessageDTO[]> {
		return this.http.get<MessageDTO[]>(this.url+"getMessage");
	}


}
