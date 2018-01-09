package ru.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;


public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification(){

        //check if there is any contact; if no then create
        app.goTo().gotoHomePage();
        if (! app.getContactHelper().isThereAContact()) {
            app.goTo().gotoAddNewPage();
            app.getContactHelper().createNewContact(new ContactData("First name", null, "Last name",
                    null, null, null, null, null, null, null,
                    null, null, null), true);
        }

        //get the list of contacts before modification
        app.goTo().gotoHomePage();
        List<ContactData> before = app.getContactHelper().getContactList();

        //contact data to modify to
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"First name", "Middle name",
                "Last name", "Nickname", "Title", "Company", "Address",
                "Home", "Home phone", "Mobile phone", "work phone",
                "Fax", null);

        //choose and modify the last contact
        app.getContactHelper().clickOnEditContact(before.size()-1);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().updateContactCreation();

        //get the list of contacts after modification
        app.goTo().gotoHomePage();
        List<ContactData> after = app.getContactHelper().getContactList();
        //compare sizes of the lists
        Assert.assertEquals(after.size(), before.size());

        //modify the last elemant in the before list
        before.remove(before.size() - 1);
        before.add(contact);

        //compare sorted by Id before and after lists
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
