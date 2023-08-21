import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map, Observable} from "rxjs";
import {Person} from "../models/person.model";

@Injectable({
    providedIn: 'root'
})
export class PersonService {

    private apiUrl = 'http://localhost:8080/person';

    constructor(private http: HttpClient) {
    }

    getPeople(): Observable<any> {
        return this.http.get<any>(this.apiUrl);
    }
}
