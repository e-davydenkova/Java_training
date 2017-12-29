package ru.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.util.Strings;
import ru.training.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

import static com.sun.xml.internal.ws.util.VersionUtil.compare;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){
        ContactData contact = new ContactData("First name", "Middle name",
                "Last name", "Nickname", "Title", "Company", "Address",
                "Home", "Home phone", "Mobile phone", "work phone",
                "Fax", null);

        //
        app.getNavigationHelper().gotoHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoAddNewPage();
            ContactData contact1 = contact;
            contact1.setGroup("test1");
            app.getContactHelper().createNewContact(contact1, true);
        }

        app.getNavigationHelper().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();

        app.getContactHelper().clickOnEditContact();
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().updateContactCreation();

        app.getNavigationHelper().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        Assert.assertEquals(after.size(), before.size());

        before.remove(0);
        before.add(contact);

        Comparator<? super ContactData> byFirstName = (g1, g2) -> g1.getFirstName().compareTo(g2.getFirstName());
        before.sort(byFirstName);
        after.sort(byFirstName);
        Assert.assertEquals(before, after);
    }
}
