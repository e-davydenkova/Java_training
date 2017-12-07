package ru.training.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;
import ru.training.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {
    FirefoxDriver wd;

    public ContactHelper(FirefoxDriver wd) {
        super(wd);
    }

    public void submitNewContactCreation() {
        click(By.name("submit"));
    }

    public void fillNewContactForm(ContactData contactData) {
        type(By.name("firstname"),contactData.getFirstName());
        type(By.name("middlename"),contactData.getMiddleName());
        type(By.name("lastname"),contactData.getLastName());
        type(By.name("nickname"),contactData.getNickname());
        type(By.name("title"),contactData.getTitle());
        type(By.name("company"),contactData.getCompany());
        type(By.name("address"),contactData.getAddress());
        type(By.name("home"),contactData.getHomePhone());
        type(By.name("mobile"),contactData.getMobilePhone());
        type(By.name("work"),contactData.getWorkPhone());
        type(By.name("fax"),contactData.getFax());
    }
}
