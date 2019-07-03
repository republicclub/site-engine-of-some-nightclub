import {HttpClientModule} from '@angular/common/http';
import {NgModule} from "@angular/core";
import {
  FormsModule,
  ReactiveFormsModule
} from '@angular/forms';
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
import {BrowserModule} from '@angular/platform-browser';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {NgbModule} from "@ng-bootstrap/ng-bootstrap";
import {DlDateTimePickerDateModule} from "angular-bootstrap-datetimepicker";
import {
  FroalaEditorModule,
  FroalaViewModule
} from "angular-froala-wysiwyg";
import {QRCodeModule} from "angularx-qrcode";
import {ToastModule} from "ng6-toastr";
import {NgxUploaderModule} from "ngx-uploader";
import {AdminOrderListComponent} from './admin/admin-order-list/admin-order-list.component';
import {AdminEventPriceComponent} from './admin/events/admin-event-price/admin-event-price.component';
import {EventsAdminPageComponent} from './admin/events/events-admin-page/events-admin-page.component';
import {EventsListComponent} from './admin/events/events-list/events-list.component';
import {MenuEditorComponent} from './admin/menu-editor/menu-editor.component';
import {NewsSummaryComponent} from './admin/news/news-summary/news-summary.component';
import {PreReportEditorComponent} from './admin/photo-report-editor/pre-report-editor/pre-report-editor.component';
import {PreReportsGalleryComponent} from './admin/photo-report-editor/pre-reports-gallery/pre-reports-gallery.component';
import {PreSummaryComponent} from './admin/photo-report-editor/pre-summary/pre-summary.component';
import {SettingsMainComponent} from './admin/settings/settings-main/settings-main.component';
import {AdminComponent} from './admin/summary/admin.component';
import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthService} from "./auth.service";
import {PasswordRestoreComponent} from './auth/password-restore/password-restore.component';
import {SingInComponent} from './auth/sing-in/sing-in.component';
import {SingUpComponent} from './auth/sing-up/sing-up.component';
import {ClubComponent} from './club/club.component';
import {ConfirmationDialogComponent} from './common-components/confirmation-dialog/confirmation-dialog.component';
import {ConfirmationDialogService} from "./common-components/confirmation-dialog/confirmation-dialog.service";
import {EventViewComponent} from './components/event-view/event-view.component';
import {TopBannerComponent} from './components/top-banner/top-banner.component';
import {EventPageComponent} from './event-page/event-page.component';
import {EventReportService} from "./event-report.service";
import {EventReportComponent} from './event-report/event-report.component';
import {EventsService} from './events.service';
import {EventsComponent} from './events/events.component';
import {FeedbackService} from "./feedback.service";
import {FeedbackComponent} from './feedback/feedback.component';
import {FooterComponent} from './footer/footer.component';
import {MediaComponent} from './media/media.component';
import {MenuService} from './menu.service';
import {NewsPageComponent} from './news-page/news-page.component';
import {NewsService} from "./news.service";
import {NewsComponent} from './news/news.component';
import {OrderComponent} from './order/order.component';
import {
  BepaidDialog,
  MenuOrderComponent
} from './orders/menu-order/menu-order.component';
import {OrderEditComponent} from './orders/order-edit/order-edit.component';
import {OrderFoodComponent} from './orders/order-food/order-food.component';
import {OrderTicketsComponent} from './orders/order-tickets/order-tickets.component';
import {OrderViewComponent} from './orders/order-view/order-view.component';
import {CashboxComponent} from './pages/cashbox/cashbox.component';
import {DiscoComponent} from './pages/disco/disco.component';
import {PageNotFoundComponent} from './pages/page-not-found/page-not-found.component';
import {PublicOfferComponent} from './pages/public-offer/public-offer.component';
import {RulesPayComponent} from './pages/rules-pay/rules-pay.component';
import {RulesComponent} from './pages/rules/rules.component';
import {PhotoReportService} from "./services/photo-report/photo-report.service";
import {PriceService} from "./services/price/price.service";
import {SettingsService} from "./services/settings/settings.service";
import {StatisticService} from "./services/statistic/statistic.service";
import {OrderTicketService} from "./services/tickets/order-ticket.service";
import {ShopMainComponent} from './shop/shop-main/shop-main.component';
import {UserCabinetModule} from "./user-cabinet/user-cabinet.module";

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
    NgbModule.forRoot(),
    NgxUploaderModule,
    QRCodeModule,
    ReactiveFormsModule,
    ToastModule.forRoot(),
    UserCabinetModule
  ],
  declarations: [
    AdminComponent,
    AdminEventPriceComponent,
    AdminOrderListComponent,
    AppComponent,
    BepaidDialog,
    ClubComponent,
    ConfirmationDialogComponent,
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
    SettingsMainComponent,
    PasswordRestoreComponent
  ],
  providers: [
    AuthService,
    ConfirmationDialogService,
    EventReportService,
    EventsService,
    FeedbackService,
    MenuService,
    NewsService,
    OrderTicketService,
    PriceService,
    PhotoReportService,
    SettingsService,
    StatisticService
  ],
  entryComponents: [
    BepaidDialog,
    ConfirmationDialogComponent,
    OrderTicketsComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
