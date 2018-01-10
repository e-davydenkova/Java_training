package ru.training.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.Contacts;
import ru.training.addressbook.model.GroupData;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }

    }

    @Test
    public void testCreateNewContact() {
        // number of contacts before creating a new one
        app.goTo().homePage();
        Contacts before = app.contact().all();

        ContactData contact = new ContactData().withFirstName("First name").withMiddleName("Middle name").
                withLastName("Last name").withNickname("Nickname").withTitle("Title").withCompany("Company").
                withAddress("Address").withHomePhone("Home phone").withMobilePhone("Mobile phone").
                withWorkPhone("Work phone").withFax("Fax").withGroup("test1");

        // create a new contact
        app.goTo().addNewPage();
        app.contact().create(contact, true);

        // number of contacts after creating a new one
        app.goTo().homePage();
       Contacts after = app.contact().all();

        // check sizes
        assertThat(after.size(), equalTo(before.size() + 1));

        // compare lists
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

}
