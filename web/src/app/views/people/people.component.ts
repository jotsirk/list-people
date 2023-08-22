import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {PersonService} from "../../services/person.service";
import {MatTableDataSource} from "@angular/material/table";
import {MatPaginator} from "@angular/material/paginator";
import {debounceTime, first, Subject, tap} from "rxjs";

@Component({
    selector: 'people-list',
    templateUrl: './people.component.html',
    styleUrls: ['./people.component.scss']
})
export class PeopleComponent implements OnInit, AfterViewInit {

    @ViewChild(MatPaginator) paginator!: MatPaginator;

    displayedColumns = ['name', 'imageUrl'];
    dataSource: MatTableDataSource<any>;
    totalElements = 0;
    currentPage = 0;
    defaultPageSize = 10;
    nameFilterValue = '';

    private keyUp = new Subject<string>();
    private debounceTime = 500;

    constructor(private personService: PersonService) {
        this.dataSource = new MatTableDataSource<any>([]);
        this.keyUp.pipe(
            debounceTime(this.debounceTime)
        ).subscribe(() => {
            this.applyFilter();
        });
    }

    ngOnInit(): void {
        this.loadData(this.nameFilterValue, this.currentPage, this.defaultPageSize);
    }

    ngAfterViewInit() {
        this.paginator.page
            .pipe(
                tap(() => this.loadData(this.nameFilterValue, this.paginator.pageIndex, this.paginator.pageSize))
            )
            .subscribe();
    }

    applyFilter() {
        this.loadData(this.nameFilterValue, 0, this.defaultPageSize);
    }

    nextPage(event: any) {
        this.currentPage = event.pageIndex;
    }

    onKeyUp() {
        this.keyUp.next(this.nameFilterValue);
    }

    private loadData(nameFilter: string, page: number, size: number) {
        this.personService.getPeople(nameFilter, page, size)
            .pipe(first())
            .subscribe((data) => {
                this.dataSource.data = data.content;
                this.totalElements = data.totalElements;
            });
    }
}
