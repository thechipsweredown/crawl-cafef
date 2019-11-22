package crawl;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CrawlCafeF {

    public static ArrayList<String> readCodeStock() throws IOException{
        ArrayList<String>  codeStock = new ArrayList<String>();
        File file = new File("conf/codeStock.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null)
            codeStock.add(st);
        br.close();
        return codeStock;
    }
    public static void main(String[] args) throws IOException {
        System.setProperty("webdriver.chrome.driver", "chromedriver");
        WebDriver chrome = new ChromeDriver();
        JavascriptExecutor js = (JavascriptExecutor) chrome;
        for(String code : readCodeStock()){
            File file = new File("conf/data/"+code+".txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
            String baseUrl = "http://s.cafef.vn/Lich-su-giao-dich-"+code+"-1.chn";
            int p = 1;
            chrome.get(baseUrl);
            while(p < 162){
                System.out.println(p + code);
                try {
                    WebElement page = chrome.findElements(By.className("CafeF_Paging")).get(0);
                    List<WebElement> temp = page.findElement(By.tagName("tr")).findElements(By.tagName("td"));
                    WebElement webElements = chrome.findElement(By.id("GirdTable2"));
                    List<WebElement> list = webElements.findElements(By.xpath("//tbody//*"));
                    for (WebElement e : list) {
                        if (e.getText().contains("/") && e.getText().contains(")") && !e.getText().contains("G") && !e.getText().contains("T")) {
                            bw.append(e.getText());
                            bw.append("\n");

                        }
                    }
                    WebElement elem = temp.get(p).findElement(By.tagName("a"));
                    js.executeScript("arguments[0].click();", elem);

                }catch (Exception e){
                    p++;
                }
            }

            bw.close();
        }
        chrome.close();
    }
}
