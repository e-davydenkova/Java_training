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

public class ContactAddToGroupTest extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().homePage();
            app.contact().create(new ContactData().withFirstName("First name"), true);
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
        Boolean canAdd = false;
        for (ContactData c : app.db().contacts()) {
            if (c.getGroups().size() < app.db().groups().size()) {
                canAdd = true;
                break;
            }
        }
        if (!canAdd) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test2"));
        }
    }

    @Test
    public void testContactAddToGroup() {
        Contacts contacts = app.db().contacts();
        Groups groupsDB = app.db().groups();
        ContactData modifiedContact = new ContactData();
//        ContactData modifiedContact = app.db().contacts().iterator().next();

        for (ContactData c : contacts) {
            if (c.getGroups().size() < groupsDB.size()) {
                modifiedContact = c;
                break;
            }
        }
        Assert.assertFalse(modifiedContact.getId() == Integer.MAX_VALUE, "All contacts are connected to all groups");

        Contacts before = app.db().contacts();
        GroupData groupAddTo = new GroupData();

        for (GroupData group : groupsDB) {
            if (!modifiedContact.getGroups().contains(group)) {
                groupAddTo = group;
                app.goTo().homePage();
                app.contact().addToGroup(modifiedContact, groupAddTo.getName());
                break;
            }
        }

        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(modifiedContact).withAdded(modifiedContact.inGroup(groupAddTo))));

    }
}
