package ru.training.addressbook.tests;

import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.GroupData;

public class ContactCreationTests extends TestBase {

    @Test
    public void testCreateNewContact() {
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }
        app.getNavigationHelper().gotoAddNewPage();
        app.getContactHelper().createNewContact(new ContactData("First name", "Middle name",
                "Last name", "Nickname", "Title", "Company", "Address",
                "Home", "Home phone", "Mobile phone", "work phone", "Fax",
                "test1"), true);
    }

}
