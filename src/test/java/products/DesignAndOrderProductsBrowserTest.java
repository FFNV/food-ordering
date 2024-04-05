package products;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DesignAndOrderProductsBrowserTest {

    private static HtmlUnitDriver browser;

    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate rest;

    @BeforeAll
    public static void setup() {
        browser = new HtmlUnitDriver();
        browser.manage().timeouts()
                .implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void givenSuccessDesignAProductPageTest() throws Exception {
        browser.get(homePageUrl());
        clickDesignAProduct();
        assertLandedOnLoginPage();
        doRegistration("testUser1", "testPassword1");
        assertLandedOnLoginPage();
        doLogin("testUser1", "testPassword1");
        assertDesignPageElements();
        buildAndSubmitAProduct("Test product1", "DBLC");
        clickBuildAnotherProduct();
        buildAndSubmitAProduct("Test product2", "LEAN", "AFGA");
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    @Test
    public void givenEmptyOrderDesignAProductPageTest() throws Exception {
        browser.get(homePageUrl());
        clickDesignAProduct();
        assertLandedOnLoginPage();
        doRegistration("testUser2", "testPassword2");
        assertLandedOnLoginPage();
        doLogin("testUser2", "testPassword2");
        assertDesignPageElements();
        buildAndSubmitAProduct("Test product2", "MOLY", "PERC", "LEAN", "AFGA");
        submitEmptyOrderForm();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    @Test
    public void givenInvalidOrderInfoDesignAProductPageTest() {
        browser.get(homePageUrl());
        clickDesignAProduct();
        assertLandedOnLoginPage();
        doRegistration("testUser3", "testPassword3");
        assertLandedOnLoginPage();
        doLogin("testUser3", "testPassword3");
        assertDesignPageElements();
        buildAndSubmitAProduct("Test product3", "AFGA");
        submitInvalidOrderInfo();
        fillInAndSubmitOrderForm();
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        doLogout();
    }

    private String homePageUrl() {
        return "http://localhost:" + port + "/";
    }

    private void clickDesignAProduct() {
        assertThat(browser.getCurrentUrl()).isEqualTo(homePageUrl());
        browser.findElementByCssSelector("a[id='design']").click();
    }

    private void assertLandedOnLoginPage() {
        assertThat(browser.getCurrentUrl()).isEqualTo(loginPageUrl());
    }

    private String loginPageUrl() {
        return homePageUrl() + "login";
    }

    private void doRegistration(String username, String password) {
        browser.findElementByLinkText("here").click();
        assertThat(browser.getCurrentUrl()).isEqualTo(registrationPageUrl());
        browser.findElementByName("username").sendKeys(username);
        browser.findElementByName("password").sendKeys(password);
        browser.findElementByName("confirm").sendKeys(password);
        browser.findElementByName("fullname").sendKeys("Test Name");
        browser.findElementByName("street").sendKeys("Test street");
        browser.findElementByName("city").sendKeys("Test city");
        browser.findElementByName("state").sendKeys("Test state");
        browser.findElementByName("zip").sendKeys("Test zip");
        browser.findElementByName("phone").sendKeys("777-777-77-77");
        browser.findElementByCssSelector("form#registerForm").submit();
    }

    private String registrationPageUrl() {
        return homePageUrl() + "register";
    }

    private void doLogin(String username, String password) {
        browser.findElementByCssSelector("input#username").sendKeys(username);
        browser.findElementByCssSelector("input#password").sendKeys(password);
        browser.findElementByCssSelector("form#loginForm").submit();
    }

    private void assertDesignPageElements() {
        assertThat(browser.getCurrentUrl()).isEqualTo(designPageUrl());
        List<WebElement> ingredintGroups = browser.findElementsByClassName("ingredient-group");
        assertThat(ingredintGroups).hasSize(5);

        WebElement packGroup = browser.findElementByCssSelector("div.ingredient-group#pack");
        List<WebElement> packs = packGroup.findElements(By.tagName("div"));
        assertThat(packs).hasSize(2);
        assertIngredient(packGroup, 0, "ZPLK", "Ziplock");
        assertIngredient(packGroup, 1, "DBLC", "Double cup");

        WebElement mjGroup = browser.findElementByCssSelector("div.ingredient-group#mj");
        List<WebElement> mjs = mjGroup.findElements(By.tagName("div"));
        assertThat(mjs).hasSize(2);
        assertIngredient(mjGroup, 0, "AFGA", "Афганка");
        assertIngredient(mjGroup, 1, "GOGL", "Gorilla Glue");

        WebElement syrupGroup = browser.findElementByCssSelector("div.ingredient-group#syrup");
        List<WebElement> syrups = syrupGroup.findElements(By.tagName("div"));
        assertThat(syrups).hasSize(2);
        assertIngredient(syrupGroup, 0, "LEAN", "Lean");
        assertIngredient(syrupGroup, 1, "PPDR", "Purlple Drank");

        WebElement mollyGroup = browser.findElementByCssSelector("div.ingredient-group#molly");
        List<WebElement> mollys = mollyGroup.findElements(By.tagName("div"));
        assertThat(mollys).hasSize(2);
        assertIngredient(mollyGroup, 0, "MDMA", "MDMA");
        assertIngredient(mollyGroup, 1, "MOLY", "Molly");

        WebElement perkGroup = browser.findElementByCssSelector("div.ingredient-group#perk");
        List<WebElement> perks = perkGroup.findElements(By.tagName("div"));
        assertThat(perks).hasSize(2);
        assertIngredient(perkGroup, 0, "XANY", "Xanny");
        assertIngredient(perkGroup, 1, "PERC", "Percocet");
    }

    private String designPageUrl() {
        return homePageUrl() + "design";
    }

    private void assertIngredient(WebElement ingredintGroup, int ingredinetIndex,
            String id, String name) {
        List<WebElement> packs = ingredintGroup.findElements(By.tagName("div"));
        WebElement ingredient = packs.get(ingredinetIndex);
        assertThat(ingredient.findElement(By.tagName("input")).getAttribute("value")).isEqualTo(id);
        assertThat(ingredient.findElement(By.tagName("span")).getText()).isEqualTo(name);
    }

    private void buildAndSubmitAProduct(String name, String... ingredients) {
        assertDesignPageElements();

        for (String ingredient : ingredients) {
            browser.findElementByCssSelector("input[value='" + ingredient + "']").click();
        }
        browser.findElementByCssSelector("input#name").sendKeys(name);
        browser.findElementByCssSelector("form#productForm").submit();
    }

    private void fillInAndSubmitOrderForm() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        fillField("input#deliveryName", "Test name");
        fillField("input#deliveryStreet", "Test street");
        fillField("input#deliveryCity", "Test city");
        fillField("input#deliveryState", "Test state");
        fillField("input#deliveryZip", "123");
        fillField("input#ccNumber", "4111111111111111");
        fillField("input#ccExpiration", "02/21");
        fillField("input#ccCVV", "123");
        browser.findElementByCssSelector("form#orderForm").submit();
    }

    private String orderDetailsPageUrl() {
        return homePageUrl() + "orders";
    }

    private void fillField(String fieldName, String value) {
        WebElement field = browser.findElementByCssSelector(fieldName);
        field.clear();
        field.sendKeys(value);
    }

    private void clickBuildAnotherProduct() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        browser.findElementByCssSelector("a[id='another']").click();
    }

    private void doLogout() {
        WebElement logOutForm = browser.findElementByCssSelector("form#logoutForm");
        if (logOutForm != null) {
            logOutForm.submit();
        }
    }

    private void submitEmptyOrderForm() {
        assertThat(browser.getCurrentUrl()).isEqualTo(currentOrderDetailsPageUrl());

        fillField("input#deliveryName", "");
        fillField("input#deliveryStreet", "");
        fillField("input#deliveryCity", "");
        fillField("input#deliveryState", "");
        fillField("input#deliveryZip", "");
        browser.findElementByCssSelector("form#orderForm").submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors)
                .hasSize(9)
                .contains(
                        "Please correct the problems below and resubmit.",
                        "Delivery name is required",
                        "Street is required",
                        "City is required",
                        "State is required",
                        "Zip code is required",
                        "Not a valid credit card number",
                        "Must be formatted MM/YY",
                        "Invalid CVV");
    }

    private List<String> getValidationErrorTexts() {
        List<WebElement> validationErrorElements = browser.findElementsByClassName("validationError");
        List<String> validationErrors = validationErrorElements.stream()
                .map(el -> el.getText())
                .collect(Collectors.toList());
        return validationErrors;
    }

    private String currentOrderDetailsPageUrl() {
        return homePageUrl() + "orders/current";
    }

    private void submitInvalidOrderInfo() {
        assertThat(browser.getCurrentUrl()).startsWith(orderDetailsPageUrl());
        fillField("input#deliveryName", "Maks");
        fillField("input#deliveryStreet", "52");
        fillField("input#deliveryCity", "Spb");
        fillField("input#deliveryState", "Len");
        fillField("input#deliveryZip", "812");
        fillField("input#ccNumber", "11111111111111111111111111111");
        fillField("input#ccExpiration", "1000/11111");
        fillField("input#ccCVV", "111111");
        browser.findElementByCssSelector("form#orderForm").submit();

        assertThat(browser.getCurrentUrl()).isEqualTo(orderDetailsPageUrl());

        List<String> validationErrors = getValidationErrorTexts();
        assertThat(validationErrors)
                .hasSize(4)
                .contains(
                        "Please correct the problems below and resubmit.",
                        "Not a valid credit card number",
                        "Must be formatted MM/YY",
                        "Invalid CVV");
    }
}
