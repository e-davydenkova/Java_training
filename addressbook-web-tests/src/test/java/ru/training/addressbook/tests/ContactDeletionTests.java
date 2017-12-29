package ru.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() {
        app.getNavigationHelper().gotoHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoAddNewPage();
            app.getContactHelper().createNewContact(new ContactData("First name", "Middle name",
                    "Last name", "Nickname", "Title", "Company", "Address",
                    "Home", "Home phone", "Mobile phone", "work phone",
                    "Fax", "test1"), true);
        }

        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().selectContact(0);
        app.getContactHelper().deleteContact();

        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        Assert.assertEquals(after.size(), before.size() - 1);

        before.remove(0);
        Assert.assertEquals(before, after);
    }
}
