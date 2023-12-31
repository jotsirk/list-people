import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {AppComponent} from './app.component';
import {PeopleComponent} from './views/people/people.component';
import {AppRoutingModule} from "./app-routing.module";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatInputModule} from "@angular/material/input";
import {MatTableModule} from "@angular/material/table";
import {MatPaginatorModule} from "@angular/material/paginator";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { BaseLayoutComponent } from './layout/base-layout/base-layout.component';
import {MatToolbarModule} from "@angular/material/toolbar";

@NgModule({
    declarations: [
        AppComponent,
        PeopleComponent,
        BaseLayoutComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatInputModule,
        MatTableModule,
        MatPaginatorModule,
        HttpClientModule,
        FormsModule,
        MatToolbarModule
    ],
    providers: [],
    bootstrap: [AppComponent]
})
export class AppModule {
}
