import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
    providedIn: 'root'
})
export class PersonService {

    private apiUrl = 'http://localhost:8080/person';

    constructor(private http: HttpClient) {
    }

    getPeople(nameFilter: string, page: number, size: number): Observable<any> {
        const params = new HttpParams()
            .set('name', nameFilter)
            .set('page', page.toString())
            .set('size', size.toString());
        return this.http.get<any>(this.apiUrl, {params});
    }
}
