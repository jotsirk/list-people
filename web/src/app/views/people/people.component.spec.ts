import {ComponentFixture, TestBed} from '@angular/core/testing';
import {PeopleComponent} from './people.component';
import {PersonService} from "../../services/person.service";
import {NO_ERRORS_SCHEMA} from "@angular/core";
import {of} from "rxjs";
import {Page, Person} from "../../models/person.model";

describe('PeopleComponent', () => {
    let component: PeopleComponent;
    let fixture: ComponentFixture<PeopleComponent>;
    let personServiceSpy: jasmine.SpyObj<PersonService>;

    beforeEach(() => {
        const spy = jasmine.createSpyObj('PersonService', ['getPeople']);

        TestBed.configureTestingModule({
            declarations: [PeopleComponent],
            providers: [
                {provide: PersonService, useValue: spy}
            ],
            schemas: [NO_ERRORS_SCHEMA]
        }).compileComponents();

        fixture = TestBed.createComponent(PeopleComponent);
        component = fixture.componentInstance;
        personServiceSpy = TestBed.inject(PersonService) as jasmine.SpyObj<PersonService>;
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should call personService.getPeople and set dataSource when loadData is called', () => {
        const mockData: Page<Person> = {
            content: [{name: 'Joosep joo', photoUrl: 'http://example.com/john.jpg'}],
            size: 10,
            number: 0,
            first: true,
            last: false,
            totalPages: 1,
            totalElements: 1
        };

        personServiceSpy.getPeople.and.returnValue(of(mockData));

        component.loadData('Joo', 0, 10);

        expect(personServiceSpy.getPeople).toHaveBeenCalledWith('Joo', 0, 10);
        expect(component.dataSource.data).toEqual(mockData.content);
    });

    it('should sanitize URL in getSafeUrl', () => {
        const unsafeUrl = 'http://someunsafeurl.com';
        const safeUrl = component.getSafeUrl(unsafeUrl);
        expect(safeUrl).toBeTruthy();
    });
});
