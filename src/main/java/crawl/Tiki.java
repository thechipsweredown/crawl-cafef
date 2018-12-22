package crawl;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Tiki {

    public static void getDetail(WebDriver chrome, JavascriptExecutor js, BufferedWriter bw) {
        while (true) {
            WebDriverWait wait = new WebDriverWait(chrome, 10);
            chrome.manage().window().maximize();
            js.executeScript("window.scrollBy(0,7000)");
            List<WebElement> webElementContent = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("review_detail")));
            List<WebElement> webElemenratting = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.className("rating-content")));
            for (int j = 0; j < webElementContent.size(); j++) {
                try {
                    // tinh toan so sao
                    String x = webElemenratting.get(j).findElement(By.tagName("span")).getCssValue("width");
                    x = x.replaceAll("px", "");
                    double numberStar = Double.parseDouble(x) / 84 * 5;

                    //in ra file
                    String content = webElementContent.get(j).getText();
                    content = content.replaceAll("\n", " ");
                    String sentiment = "";

                    double start = Math.ceil(numberStar);
                    if (start > 3) {
                        sentiment = "positive";
                    } else if (start > 2 && start < 4) {
                        sentiment = "medium";
                    } else {
                        sentiment = "negative";
                    }


                    JSONObject json = new JSONObject();
                    json.put("comment", content);
                    json.put("star", start);
                    bw.write(json.toString());
                    bw.newLine();
                } catch (Exception e) {
                }
            }
            try {
                List<WebElement> element = chrome.findElements(By.className("next"));
                if (element.size() != 0) {
                    js.executeScript("arguments[0].click();", element.get(0));
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "/home/trandem/chromedriver");//chinh den chorm driver doawn tren mang nhe
        WebDriver chrome = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) chrome;
        File file = new File("dien_lanh");//ten file output
        BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));

        for (int pageId = 1; pageId <= 10; pageId++) {
		//link nay ong tu thay doi nhe
            String baseUrl = "https://tiki.vn/dien-gia-dung-dien-lanh/c1882?src=mega-menu&rating=1&page="+ pageId;
            System.out.println(baseUrl);
            try {
                chrome.get(baseUrl);
                int dem = 0;
                List<WebElement> webElements = chrome.findElements(By.className("product-item    "));
                System.out.println( webElements.size());
                for (int i = 0; i < webElements.size(); i++) {
                    dem++;
                    System.out.println(dem);
                    if (i == 3) {
                        webElements = chrome.findElements(By.className("product-item    "));
                        continue;
                    }
                    if (i == 23) break;
                    webElements.get(i).click();
                    try {
                        getDetail(chrome, js, bw);

                    } catch (Exception e) {

                    }
                    chrome.navigate().back();
                    webElements = chrome.findElements(By.className("product-item    "));
                }
            } catch (Exception e) {

            }
        }
        bw.close();
        chrome.close();
    }
}
