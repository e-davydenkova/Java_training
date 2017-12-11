package ru.training.addressbook.tests;

import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().clickOnEditContact();
        app.getContactHelper().fillContactForm(new ContactData("First name", "Middle name", "Last name", "Nickname", "Title", "Company", "Address", "Home", "Home phone", "Mobile phone", "work phone", "Fax", null), false);
        app.getContactHelper().updateContactCreation();
    }
}
