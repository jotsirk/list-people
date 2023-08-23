import {ComponentFixture, TestBed} from '@angular/core/testing';
import {BaseLayoutComponent} from './base-layout.component';
import {By} from "@angular/platform-browser";

describe('BaseLayoutComponent', () => {
    let component: BaseLayoutComponent;
    let fixture: ComponentFixture<BaseLayoutComponent>;

    beforeEach(async () => {
        await TestBed.configureTestingModule({
            declarations: [BaseLayoutComponent]
        })
            .compileComponents();
    });

    beforeEach(() => {
        fixture = TestBed.createComponent(BaseLayoutComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });

    it('should render the toolbar', () => {
        const toolbar = fixture.debugElement.query(By.css('mat-toolbar'));
        expect(toolbar).toBeTruthy();
    });

    it('should render the people-list component', () => {
        const peopleList = fixture.debugElement.query(By.css('people-list'));
        expect(peopleList).toBeTruthy();
    });
});
