import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {PeopleComponent} from "./views/people/people.component";

const routes: Routes = [
    {path: '', component: PeopleComponent},
    {path: '**', redirectTo: ''},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
