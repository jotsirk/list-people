import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {environment} from "../../environments/environment";
import {Page, Person} from "../models/person.model";

@Injectable({
    providedIn: 'root'
})
export class PersonService {

    private apiUrl = `${environment.apiUrl}/person`;

    constructor(private http: HttpClient) {
    }

    getPeople(nameFilter: string, page: number, size: number): Observable<Page<Person>> {
        const params = new HttpParams()
            .set('name', nameFilter)
            .set('page', page.toString())
            .set('size', size.toString());
        return this.http.get<any>(this.apiUrl, {params});
    }
}
