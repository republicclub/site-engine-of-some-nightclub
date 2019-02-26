import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {EventsComponent} from "./events/events.component";
import {ClubComponent} from "./club/club.component";
import {MediaComponent} from "./media/media.component";
import {NewsComponent} from "./news/news.component";
import {NewsPageComponent} from "./news-page/news-page.component";
import {EventPageComponent} from "./event-page/event-page.component";
import {EventReportComponent} from "./event-report/event-report.component";
import {AdminComponent} from "./admin/summary/admin.component";
import {AdminOrderListComponent} from "./admin/admin-order-list/admin-order-list.component";
import {EventsListComponent} from "./admin/events/events-list/events-list.component";
import {EventViewComponent} from "./components/event-view/event-view.component";
import {EventsAdminPageComponent} from "./admin/events/events-admin-page/events-admin-page.component";
import {FeedbackComponent} from "./feedback/feedback.component";
import {MenuEditorComponent} from "./admin/menu-editor/menu-editor.component";
import {DiscoComponent} from "./pages/disco/disco.component";
import {MenuOrderComponent} from "./orders/menu-order/menu-order.component";
import {CashboxComponent} from "./pages/cashbox/cashbox.component";
import {OrderComponent} from "./order/order.component";
import {PreSummaryComponent} from "./admin/photo-report-editor/pre-summary/pre-summary.component";
import {PageNotFoundComponent} from "./pages/page-not-found/page-not-found.component";
import {RulesComponent} from "./pages/rules/rules.component";
import {RulesPayComponent} from "./pages/rules-pay/rules-pay.component";
import {NewsSummaryComponent} from "./admin/news/news-summary/news-summary.component";
import {ShopMainComponent} from "./shop/shop-main/shop-main.component";
import {PublicOfferComponent} from "./pages/public-offer/public-offer.component";
import {SingInComponent} from "./auth/sing-in/sing-in.component";
import {SingUpComponent} from "./auth/sing-up/sing-up.component";
import {PersonalInfoComponent} from "./user-cabinet/personal-info/personal-info.component";
import {SettingsMainComponent} from "./admin/settings/settings-main/settings-main.component";

const routes: Routes = [
  {path: '', component: EventsComponent},
  {path: 'afisha', component: EventsComponent},
  {path: 'null', component: EventsComponent},
  {path: 'orders', component: MenuOrderComponent},
  {path: 'media', component: MediaComponent},
  {path: 'news', component: NewsComponent},
  {path: 'club', component: ClubComponent},
  {path: 'event/:id', component: EventPageComponent},
  {path: 'events/:sameData', component: DiscoComponent},
  {path: 'events/:sameData/:anotherData', component: DiscoComponent},
  {path: 'event-reports/:id', component: EventReportComponent},
  {path: 'news/:id', component: NewsPageComponent},
  {path: 'admin', component: AdminComponent},
  {path: 'admin-order-list', component: AdminOrderListComponent},
  {path: 'admin-events-list', component: EventsListComponent},
  {path: 'admin-event-view', component: EventViewComponent},
  {path: 'admin-events', component: EventsAdminPageComponent},
  {path: 'admin-news', component: NewsSummaryComponent},
  {path: 'admin-settings', component: SettingsMainComponent},
  {path: 'login', component: SingInComponent},
  {path: 'singup', component: SingUpComponent},
  {path: 'feedback', component: FeedbackComponent},
  {path: 'admin-menu-editor', component: MenuEditorComponent},
  {path: 'cashbox', component: CashboxComponent},
  {path: 'order/:uuid', component: OrderComponent},
  {path: 'admin-photo-report-editor', component: PreSummaryComponent},
  {path: 'rules', component: RulesComponent},
  {path: 'rules-pay', component: RulesPayComponent},
  {path: 'public-offer', component: PublicOfferComponent},
  {path: 'shop', component: ShopMainComponent},
  {path: 'private/person', component: PersonalInfoComponent},
  {
    path: '**', component: PageNotFoundComponent, data: { breadcrumb: 'page-not-found', roles: [] }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
