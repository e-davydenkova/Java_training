package ru.training.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;


public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("First name"), true);
        }
    }

    @Test
    public void testContactModification() {
        //read data from db, not UI
        Contacts before = app.db().contacts();

        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("First name").
                withMiddleName("Middle name").withLastName("Last name").withNickname("Nickname").
                withTitle("Title").withCompany("Company").withAddress("Address").withHomePhone("Home phone").
                withMobilePhone("Mobile phone").withWorkPhone("Work phone").withFax("Fax");

        app.goTo().homePage();
        app.contact().modify(contact);

        Contacts after = app.db().contacts();

        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
    }
}
