package com.saurav.UBS;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class WebTable {

	public static void main(String[] args) throws InterruptedException {
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("file:///C:/Users/singh/Desktop/ubs.html");
		driver.manage().window().maximize();
		Map<String, String> continent = new HashMap<>();
		Map<String, Integer> CountryDeathCount = new HashMap<>();
		WebElement table = driver.findElement(By.xpath("/html/body/table"));
		List<WebElement> rowCount = table.findElements(By.tagName("tr"));
		String beforexpath = "/html/body/table/tbody/tr[";
		String afterxpath_Count = "]/td[3]";
		String afterxpath_Country = "]/td[1]";
		String afterxpath_Continent = "]/td[2]";
		for (int i = 2; i <= rowCount.size(); i++) {
			int sum = 0;
			String actualxpath_Count = beforexpath + i + afterxpath_Count;
			String actualxpath_Country = beforexpath + i + afterxpath_Country;
			String actualxpath_Continent = beforexpath + i + afterxpath_Continent;
			String country = table.findElement(By.xpath(actualxpath_Country)).getText();
			String countryCount = table.findElement(By.xpath(actualxpath_Count)).getText();
			Integer cc1 = Integer.valueOf(countryCount);
			String continentName = table.findElement(By.xpath(actualxpath_Continent)).getText();
			CountryDeathCount.put(country, cc1);
			boolean containsKey = continent.containsKey(continentName);
			if (!containsKey) {
				continent.put(continentName, countryCount);
			} else {
				sum = Integer.valueOf(continent.get(continentName));
				int num = Integer.valueOf(countryCount);
				sum += num;
				String cc = String.valueOf(sum);
				continent.put(continentName, cc);
			}
		}
		System.out.println(continent);
		System.out.println(CountryDeathCount);
		int max = Collections.max(CountryDeathCount.values());
		CountryDeathCount.entrySet().stream().filter(entry -> entry.getValue() == max).map(entry -> entry.getKey())
				.collect(Collectors.toList());
		System.out.println(max);
		for (Map.Entry<String, Integer> m : CountryDeathCount.entrySet()) {
			int maxCount = (int) m.getValue();
			if (maxCount == max) {
				System.out.println(m.getKey() + " " + m.getValue());
			}
		}

		driver.close();

	}

}
