import {BrowserModule} from '@angular/platform-browser';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {EventsComponent} from './events/events.component';
import {EventsService} from './events.service';
import {MenuService} from './menu.service';
import {FooterComponent} from './footer/footer.component';
import {ClubComponent} from './club/club.component';
import {MediaComponent} from './media/media.component';
import {NewsComponent} from './news/news.component';
import {NewsPageComponent} from './news-page/news-page.component';
import {EventPageComponent} from './event-page/event-page.component';
import {EventReportComponent} from './event-report/event-report.component';
import {NewsService} from "./news.service";
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {AdminComponent} from './admin/summary/admin.component';
import {AdminOrderListComponent} from './admin/admin-order-list/admin-order-list.component';
import {NgModule} from "@angular/core";
import {EventsListComponent} from './admin/events/events-list/events-list.component';
import {EventViewComponent} from './components/event-view/event-view.component';
import {
  MatAutocompleteModule,
  MatBadgeModule,
  MatBottomSheetModule,
  MatButtonModule,
  MatButtonToggleModule,
  MatCardModule,
  MatCheckboxModule,
  MatChipsModule,
  MatDatepickerModule,
  MatDialogModule,
  MatDividerModule,
  MatExpansionModule,
  MatFormFieldModule,
  MatGridListModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatMenuModule,
  MatNativeDateModule,
  MatPaginatorModule,
  MatProgressBarModule,
  MatProgressSpinnerModule,
  MatRadioModule,
  MatRippleModule,
  MatSelectModule,
  MatSidenavModule,
  MatSliderModule,
  MatSlideToggleModule,
  MatSnackBarModule,
  MatSortModule,
  MatStepperModule,
  MatTableModule,
  MatTabsModule,
  MatToolbarModule,
  MatTooltipModule,
  MatTreeModule,
} from '@angular/material';
import {EventsAdminPageComponent} from './admin/events/events-admin-page/events-admin-page.component';
import {EventReportService} from "./event-report.service";
import {AuthService} from "./auth.service";
import {FeedbackComponent} from './feedback/feedback.component';
import {FeedbackService} from "./feedback.service";
import {MenuEditorComponent} from './admin/menu-editor/menu-editor.component';
import {DlDateTimePickerDateModule} from "angular-bootstrap-datetimepicker";
import {PhotoReportService} from "./services/photo-report/photo-report.service";
import {DiscoComponent} from './pages/disco/disco.component';
import {BepaidDialog, MenuOrderComponent} from './orders/menu-order/menu-order.component';
import {OrderViewComponent} from './orders/order-view/order-view.component';
import {OrderEditComponent} from './orders/order-edit/order-edit.component';
import {OrderFoodComponent} from './orders/order-food/order-food.component';
import {CashboxComponent} from './pages/cashbox/cashbox.component';
import {ToastModule} from "ng6-toastr";
import {OrderComponent} from './order/order.component';
import {PreSummaryComponent} from './admin/photo-report-editor/pre-summary/pre-summary.component';
import {PreReportsGalleryComponent} from './admin/photo-report-editor/pre-reports-gallery/pre-reports-gallery.component';
import {PreReportEditorComponent} from './admin/photo-report-editor/pre-report-editor/pre-report-editor.component';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {TopBannerComponent} from './components/top-banner/top-banner.component';
import {StatisticService} from "./services/statistic/statistic.service";
import {NgxUploaderModule} from "ngx-uploader";
import {RulesComponent} from './pages/rules/rules.component';
import {RulesPayComponent} from './pages/rules-pay/rules-pay.component';
import {FroalaEditorModule, FroalaViewModule} from "angular-froala-wysiwyg";
import {NewsSummaryComponent} from './admin/news/news-summary/news-summary.component';
import {OrderTicketsComponent} from './orders/order-tickets/order-tickets.component';
import {OrderTicketService} from "./services/tickets/order-ticket.service";
import {ShopMainComponent} from './shop/shop-main/shop-main.component';
import {PublicOfferComponent} from './pages/public-offer/public-offer.component';
import {SingUpComponent} from './auth/sing-up/sing-up.component';
import {SingInComponent} from './auth/sing-in/sing-in.component';
import {QRCodeModule} from "angularx-qrcode";
import {UserCabinetModule} from "./user-cabinet/user-cabinet.module";
import {SettingsMainComponent} from './admin/settings/settings-main/settings-main.component';
import {SettingsService} from "./services/settings/settings.service";

@NgModule({
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    DlDateTimePickerDateModule,
    FormsModule,
    FroalaEditorModule.forRoot(),
    FroalaViewModule.forRoot(),
    HttpClientModule,
    MatAutocompleteModule,
    MatBadgeModule,
    MatBottomSheetModule,
    MatButtonModule,
    MatButtonToggleModule,
    MatCardModule,
    MatCheckboxModule,
    MatChipsModule,
    MatDatepickerModule,
    MatDialogModule,
    MatDividerModule,
    MatExpansionModule,
    MatFormFieldModule,
    MatGridListModule,
    MatIconModule,
    MatInputModule,
    MatListModule,
    MatMenuModule,
    MatNativeDateModule,
    MatPaginatorModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatRadioModule,
    MatRippleModule,
    MatSelectModule,
    MatSidenavModule,
    MatSliderModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    MatSortModule,
    MatStepperModule,
    MatTableModule,
    MatTabsModule,
    MatToolbarModule,
    MatTooltipModule,
    MatTreeModule,
    NgxUploaderModule,
    QRCodeModule,
    ReactiveFormsModule,
    ToastModule.forRoot(),
    UserCabinetModule
  ],
  declarations: [
    AdminComponent,
    AdminOrderListComponent,
    AppComponent,
    BepaidDialog,
    ClubComponent,
    EventPageComponent,
    EventReportComponent,
    EventsAdminPageComponent,
    EventsComponent,
    EventsListComponent,
    EventViewComponent,
    FeedbackComponent,
    FooterComponent,
    MediaComponent,
    MenuEditorComponent,
    NewsComponent,
    NewsPageComponent,
    NewsSummaryComponent,
    DiscoComponent,
    MenuOrderComponent,
    OrderViewComponent,
    OrderEditComponent,
    OrderFoodComponent,
    CashboxComponent,
    OrderComponent,
    OrderTicketsComponent,
    PreSummaryComponent,
    PreReportsGalleryComponent,
    PreReportEditorComponent,
    PublicOfferComponent,
    PageNotFoundComponent,
    RulesComponent,
    RulesPayComponent,
    ShopMainComponent,
    SingInComponent,
    SingUpComponent,
    TopBannerComponent,
    SettingsMainComponent
  ],
  providers: [
    AuthService,
    EventReportService,
    EventsService,
    FeedbackService,
    MenuService,
    NewsService,
    OrderTicketService,
    PhotoReportService,
    SettingsService,
    StatisticService
  ],
  entryComponents: [BepaidDialog, OrderTicketsComponent],
  bootstrap: [AppComponent]
})
export class AppModule {
}
