import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {Person} from "../../models/person.model";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {first} from "rxjs";

@Component({
    selector: 'people-list',
    templateUrl: './people.component.html',
    styleUrls: ['./people.component.scss']
})
export class PeopleComponent implements OnInit, AfterViewInit {
    displayedColumns = ['name', 'photoUrl'];
    dataSource: MatTableDataSource<Person>;

    @ViewChild(MatPaginator) paginator!: MatPaginator;

    people: Person[] = [];

    constructor(private personService: PersonService) {
        this.dataSource = new MatTableDataSource(this.people);
    }

    ngOnInit(): void {
        this.loadData();
    }

    ngAfterViewInit() {
        this.dataSource.paginator = this.paginator;
        this.dataSource = new MatTableDataSource(this.people);
    }

    applyFilter(filterValue: string) {
        filterValue = filterValue.trim(); // Remove whitespace
        filterValue = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
        this.dataSource.filter = filterValue;
    }

    private loadData() {
        this.personService.getPeople()
            .pipe(first())
            .subscribe((people) => {
                console.log(people.content);
                this.people = people
            });
    }
}
