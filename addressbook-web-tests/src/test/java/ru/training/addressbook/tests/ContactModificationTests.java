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
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData().withFirstName("First name"), true);
        }
    }

    @Test
    public void testContactModification(){

        app.goTo().homePage();
        Contacts before = app.contact().all();

        ContactData modifiedContact = before.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("First name").
                withMiddleName("Middle name").withLastName("Last name").withNickname("Nickname").
                withTitle("Title").withCompany("Company").withAddress("Address").withHomePhone("Home phone").
                withMobilePhone("Mobile phone").withWorkPhone("Work phone").withFax("Fax");

        app.contact().modify(contact);

        app.goTo().homePage();
        Contacts after = app.contact().all();

        assertEquals(after.size(), before.size());
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

    }
}
