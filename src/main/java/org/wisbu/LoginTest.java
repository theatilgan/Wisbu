package org.wisbu;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.Locale;

import static com.microsoft.playwright.Locator.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LoginTest {
    Playwright playwright;
    Browser browser;
    Page page;

    String testUser = "Atılgan Aktaş";
    String websiteUrl = "https://demo.wisbu.net/home"; //
    String websiteAuthUrl = "https://demo.wisbu.net/pages/authentication/login";
    String dashboardUrl = "https://demo.wisbu.net/pages/profile/property-manager";

    @BeforeClass
    public void setUp(){
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @Test (priority = 1)
    public void testLogin(){

        // Open the website
        page.navigate(websiteUrl);

        page.locator("#nav-signin").click();  // Click the login button

        assertThat(page).hasURL(websiteAuthUrl); // Check if url is now the login page

        page.locator("#login-email").fill("atilganaktas@gmail.com"); // Fill the email field

        page.locator("#login-password").fill("Ati123gan."); // Fill the password field

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Giriş Yap")).click(); // Click the login button

        page.locator("#navbarUserDropdown").click(); // Click the user dropdown

        page.locator("//html/body/app-root/horizontal-layout/app-navbar/div[2]/ul/li/div/a[1]").click(); // Click the profile link

        Locator kodElement = page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-account-sites/div/div/section[2]/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/div/p"); // Get the code of the first site

        assertThat(kodElement).containsText("390802465"); // Check if the code is correct
    }

    @Test (priority = 2)
    public void testBorclandir(){

        String testTutar = "1500";
        String testAciklama = "Test - borç ekle";

        page.navigate(dashboardUrl);

        page.locator("//html/body/app-root/horizontal-layout/div[1]/app-menu/horizontal-menu/div/ul/li[2]/a/span").hover(); // Hover over the "Tanımlar" menu

        page.locator("//html/body/app-root/horizontal-layout/div[1]/app-menu/horizontal-menu/div/ul/li[2]/ul/li[5]/a").click(); // Click the "Kişiler" link

        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-user-profile/div/div/section/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[2]").click(); // Click the first user

        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-user-profile-detail/div/div/section[1]/div/div/div/div[2]/div[3]/div[1]/button").click(); // Click the "Borç ekle" button

        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).first().click(); // Click the "" button

        // page.getByText("26").nth(1).click(); // Click the 26th day

        // page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).nth(1).click(); // Click the "" button

        // page.getByText("30").nth(3).click(); // Click the 30th day

        page.getByPlaceholder("Tutar").click(); // Click the "Tutar" field

        page.getByPlaceholder("Tutar").fill(testTutar); // Fill the "Tutar" field

        page.locator("#field_paymentGroup").getByRole(AriaRole.TEXTBOX).click(); // Click the "Tür" field

        page.locator("//html/body/ngb-modal-window/div/div/app-debit-update/form/div[2]/div[5]/div[2]/ng-select/ng-dropdown-panel/div/div[2]/div[6]").click(); // Click the "Diğer" option

        page.locator("#field_apartment").getByRole(AriaRole.TEXTBOX).click(); // Click the "Daire" field

        page.locator("//html/body/ngb-modal-window/div/div/app-debit-update/form/div[2]/div[6]/div[2]/ng-select/ng-dropdown-panel/div/div[2]/div[2]").click(); // Click the first apartment

        Locator kisiElement = page.locator("//html/body/ngb-modal-window/div/div/app-debit-update/form/div[2]/div[7]/div[2]/ng-select/div/div/div[2]");

        assertThat(kisiElement).containsText(testUser); // Check if the user is correct

        page.getByPlaceholder("Açıklama").fill(testAciklama); // Fill the "Açıklama" field

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Kaydet")).click(); // Click the "Kaydet" button

        Locator tableTur = page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-user-profile-detail/div/div/section[2]/div[2]/div/app-manager-balance/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[3]/div/div/p");

        Locator tableAciklama = page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-user-profile-detail/div/div/section[2]/div[2]/div/app-manager-balance/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[4]/div/div/p/span");

        Locator tableMeblag = page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-user-profile-detail/div/div/section[2]/div[2]/div/app-manager-balance/div/ngx-datatable/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper[1]/datatable-body-row/div[2]/datatable-body-cell[5]/div/div/p");

        assertThat(tableTur).containsText("Diğer"); // Check if the type is correct
        assertThat(tableAciklama).containsText(testAciklama); // Check if the description is correct
        assertThat(tableMeblag).containsText("₺ -1.500,00"); // Check if the amount is correct





    }

    @Test (priority = 3)
    public void testTahsilat(){

        String testAciklama = "Test - Tahsilat";
        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-user-profile-detail/div/div/section[1]/div/div/div/div[2]/div[3]/div[2]/button/span").click(); // Click the "Tahsil et" button
        page.pause();

        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-payment-create-pm/div/div/form/div/div[2]/div/div[2]/div[2]/ngx-datatable[1]/div/datatable-body/datatable-selection/datatable-scroller/datatable-row-wrapper/datatable-body-row/div[2]/datatable-body-cell[1]/div/div/div").first().click(); // click checkbox

        page.locator("#field_paymentMethod").click(); // Click the "Tahsilat Yöntemi" field

        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-payment-create-pm/div/div/form/div/div[1]/div/div/div[1]/ng-select/ng-dropdown-panel/div/div[2]/div[1]").click(); // Click the first option

        Locator kisi = page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-payment-create-pm/div/div/form/div/div[1]/div/div/div[2]/ng-select/div/div/div[2]");

        assertThat(kisi).containsText(testUser); // Check if the user is correct

        page.locator("#field_apartment").click(); // Click the "Daire" field

        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-payment-create-pm/div/div/form/div/div[1]/div/div/div[3]/ng-select/ng-dropdown-panel/div/div[2]/div[2]").click(); // Click the first apartment

        //page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).first().click();

        //page.getByText("26").nth(1).click(); // Click the 26th day

        page.locator("#field_description").fill(testAciklama); // Fill the "Açıklama" field

        page.locator("//html/body/app-root/horizontal-layout/div[2]/content/div/app-payment-create-pm/div/div/form/div/div[1]/div/div/div[7]/button").click(); // Click the "Kaydet" button

        Locator totalTable = page.locator("datatable-body-row").last(); // Click the last row

        String totalTutar = totalTable.locator("input").inputValue(); // Get the amount

        totalTutar ="₺ "+totalTutar;

        String onayTutar = page.locator("h2.swal2-title.text-success.font-medium-5.font-weight-bolder").innerText(); // Get tutar

        Assert.assertEquals(onayTutar, totalTutar); // Check if the amount is correct

        page.locator("button.swal2-confirm.btn.btn-success.swal2-styled").click(); // Click the "Onayla" button

        String dekontTutar = page.locator("td").nth(3).locator("span").innerText(); // Check if the description is correct

        Assert.assertEquals(dekontTutar, totalTutar); // Check if the amount is correct


        page.locator("div.modal-footer.d-flex.justify-content-center").locator("button").click(); // Click the "Tamam" button


    }

    @AfterClass
    public void tearDown(){
        page.close();
        playwright.close();
        playwright.close();
    }

}
