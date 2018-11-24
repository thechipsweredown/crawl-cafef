package crawl;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Crawl {
    public static void main(String[] args) {
//        String browser = System.getenv("BROWSER");
//        System.out.println(browser);
        System.setProperty("webdriver.chrome.driver","/home/trandem/chromedriver");
        WebDriver chrome = new ChromeDriver();
        String baseUrl = "https://shopee.vn/main-giga-h61-sp2-ver-3.0-i.40433641.1014177386";

        JavascriptExecutor js = (JavascriptExecutor)chrome;
        chrome.get(baseUrl);
        HashSet<String> sets =new HashSet<String>();
        sets.add("1");

//        for(int i =0 ; i<= 5; i++){
//            chrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(chrome, 10);
//            wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("review_detail")));
            chrome.manage().window().maximize();
            js.executeScript("window.scrollBy(0,3000)");
            List<WebElement> webElementContent = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("shopee-product-rating__content")));
//            List<WebElement> webElementContent = chrome.findElements(By.className("review_detail"));
//            List<WebElement> webElemenratting = chrome.findElements(By.className("rating-content"));
            List<WebElement> webElemenratting = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("shopee-product-rating__rating")));
            for(int j =0; j< webElemenratting.size(); j++){
                try {
                    List<WebElement> rateting= webElemenratting.get(j).findElements(By.tagName("svg"));
                    for (WebElement webElement : rateting){
                        System.out.println(webElement.findElement(By.tagName("polygon")).getAttribute("fill"));
                    }
                    System.out.println("lol");
//                    System.out.println(webElementContent.get(j).getText() +"\t" +x);
                }catch (Exception e){
                    System.out.println("loi");
                }

            }
//            List<WebElement>  elements = chrome.findElement(By.className("list-pager")).findElements(By.tagName("a"));
//            for (WebElement element : elements){
//                String pageNumber = element.getText();
//                if(!sets.contains(pageNumber)){
//                    System.out.println(pageNumber);
//                    js.executeScript("arguments[0].click();", element);
//                    sets.add(pageNumber);
//                    break;
//                }
//            }
//        }
        chrome.close();
    }
}
