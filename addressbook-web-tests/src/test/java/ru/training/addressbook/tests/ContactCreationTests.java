package ru.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

    @Test
    public void testCreateNewContact() {
        // check if any groups present to connect with a group; if no then create
        app.getNavigationHelper().gotoGroupPage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }

        ContactData contact = new ContactData("First name", "Middle name",
                "Last name", "Nickname", "Title", "Company", "Address",
                "Home", "Home phone", "Mobile phone", "work phone", "Fax",
                "test1");

        // number of contacts before creating a new
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();

        // create a new contact
        app.getNavigationHelper().gotoAddNewPage();
        app.getContactHelper().createNewContact(contact, true);

        // number of contacts after creating a new one
        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();

        // check sizes of the lists
        Assert.assertEquals(after.size(), before.size() + 1);

        // compare lists
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
