package ru.training.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.Contacts;
import ru.training.addressbook.model.GroupData;
import ru.training.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactRemoveFromGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("First name"), true);
        }
    }

    @Test
    public void testContactRemoveFromGroup() {

        Groups groupsDB = app.db().groups();
        GroupData group = new GroupData();
        ContactData contact = new ContactData();

        for (GroupData g : groupsDB) {
            if (g.getContacts().size() > 0) {
                group = g;
                contact = g.getContacts().iterator().next();
                break;
            }
        }
        Assert.assertFalse(group.getId() == Integer.MAX_VALUE, "No contacts connected to any groups");

        Groups before = app.db().groups();

        app.goTo().homePage();
        app.contact().removeContact(contact, group.getName());

        Groups after = app.db().groups();
        assertThat(after, equalTo(before.without(group).withAdded(group.removeContact(contact))));

    }
}
