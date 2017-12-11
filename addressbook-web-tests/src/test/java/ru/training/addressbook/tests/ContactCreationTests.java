package ru.training.addressbook.tests;

import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testCreateNewContact() {
        app.getNavigationHelper().gotoAddNewPage();
        app.getContactHelper().fillContactForm(new ContactData("First name", "Middle name", "Last name", "Nickname", "Title", "Company", "Address", "Home", "Home phone", "Mobile phone", "work phone", "Fax", "test1"), true);
        app.getContactHelper().submitNewContactCreation();
    }

}
