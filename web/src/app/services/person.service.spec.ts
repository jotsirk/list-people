import {TestBed} from '@angular/core/testing';
import {PersonService} from './person.service';
import {HttpClientTestingModule, HttpTestingController} from "@angular/common/http/testing";
import {Page, Person} from "../models/person.model";
import {environment} from "../../environments/environment";
import {HttpErrorResponse} from "@angular/common/http";

describe('PersonService', () => {
    let service: PersonService;
    let httpMock: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            imports: [HttpClientTestingModule],
            providers: [PersonService]
        });
        service = TestBed.inject(PersonService);
        httpMock = TestBed.inject(HttpTestingController);
    });

    afterEach(() => {
        httpMock.verify();
    });

    it('should be created', () => {
        expect(service).toBeTruthy();
    });

    it('should return a list of people', () => {
        const mockData: Page<Person> = {
            content: [{name: 'Joosep joo', photoUrl: 'http://example.com/john.jpg'}],
            size: 10,
            number: 0,
            first: true,
            last: false,
            totalPages: 1,
            totalElements: 1
        };

        service.getPeople('Joo', 0, 10).subscribe(data => {
            expect(data.content.length).toBe(1);
            expect(data.content[0].name).toBe('Joosep joo');
        });

        const req = httpMock.expectOne(`${environment.apiUrl}/person?name=Joo&page=0&size=10`);
        expect(req.request.method).toBe('GET');
        req.flush(mockData);
    });

    it('should return an empty list', () => {
        const mockData: Page<Person> = {
            content: [],
            size: 10,
            number: 0,
            first: true,
            last: false,
            totalPages: 0,
            totalElements: 0
        };

        service.getPeople('invalid', 0, 10).subscribe(data => {
            expect(data.content.length).toBe(0);
        });

        const req = httpMock.expectOne(`${environment.apiUrl}/person?name=invalid&page=0&size=10`);
        expect(req.request.method).toBe('GET');
        req.flush(mockData);
    });

    it('should display an error when an exception is thrown', () => {
        service.getPeople('someerror', 0, 10).subscribe(
            () => fail('Should have failed with an error'),
            (error: HttpErrorResponse) => expect(error.error).toContain('Error occurred')
        );

        const req = httpMock.expectOne(`${environment.apiUrl}/person?name=someerror&page=0&size=10`);
        expect(req.request.method).toBe('GET');
        req.flush('Error occurred', {status: 500, statusText: 'Server Error'});
    });
});
