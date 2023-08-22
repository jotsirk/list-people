import {RouterModule, Routes} from "@angular/router";
import {NgModule} from "@angular/core";
import {BaseLayoutComponent} from "./layout/base-layout/base-layout.component";

const routes: Routes = [
    {path: '', component: BaseLayoutComponent},
    {path: '**', redirectTo: ''},
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule],
})
export class AppRoutingModule {
}
