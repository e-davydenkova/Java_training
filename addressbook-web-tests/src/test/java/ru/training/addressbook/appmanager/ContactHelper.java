package ru.training.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void submitNewContactCreation() {
        click(By.name("submit"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
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

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void updateContactCreation() {
        click(By.name("update"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
    }

    public void clickOnEditContact(int id) {
        wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
    }

    public void deleteSelectedContact() {
        click(By.cssSelector("input[value=Delete]"));
        acceptAlert();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteSelectedContact();
//        click(By.xpath("//div[@id='content']/form[2]/div[2]/input"));

    }

    public void create(ContactData contactData, boolean creation) {
        fillContactForm(contactData, creation);
        submitNewContactCreation();
    }

    public void modify(ContactData contact) {
        clickOnEditContact(contact.getId());
        fillContactForm(contact, false);
        updateContactCreation();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public Contacts all() {
        Contacts contacts = new Contacts();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement element : rows){
            String firstName = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String lastName = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            String allPhones = element.findElement(By.cssSelector("td:nth-child(6)")).getText();
            String allEmails = element.findElement(By.cssSelector("td:nth-child(5)")).getText();
            String address = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contacts.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName)
                    .withAllPhones(allPhones).withAllEmails(allEmails).withAddress(address));
        }
        return contacts;
    }

    public ContactData infoFromEditForm(ContactData contact) {
        clickOnEditContact(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String email = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getAttribute("value");
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
                .withEmail(email).withEmail2(email2).withEmail3(email3).withAddress(address);

    }
}
