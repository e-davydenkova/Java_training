package ru.training.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.training.addressbook.model.GroupData;
import ru.training.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }

    }

    @Test
    public void testGroupDeletion() {
        //read data from db, not UI
        Groups before = app.db().groups();
//        Groups before = app.group().all();
        GroupData deletedGroup = before.iterator().next();

        app.goTo().groupPage();
        app.group().delete(deletedGroup);
        assertThat(app.group().count(), equalTo(before.size() - 1));

        Groups after = app.db().groups();
//        Groups after = app.group().all();
        assertThat(after, equalTo(before.without(deletedGroup)));
    }

}
