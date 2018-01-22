package ru.training.addressbook.tests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.training.addressbook.model.ContactData;
import ru.training.addressbook.model.Contacts;
import ru.training.addressbook.model.GroupData;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }

    }

    @DataProvider
    public Iterator<Object[]> validContactsFromJSON() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/resources/contacts.json")));
        String json = "";
        String line = reader.readLine();
        while (line != null) {
            json += line;
            line = reader.readLine();
        }
        Gson gson = new Gson();
        List<ContactData> contacts = gson.fromJson(json, new TypeToken<List<ContactData>>(){}.getType());
        return contacts.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
    }

    @Test(dataProvider = "validContactsFromJSON")
    public void testCreateNewContact(ContactData contact) {
        // number of contacts before creating a new one

        Contacts before = app.db().contacs();

        // connect contact to a group
        contact.inGroup(app.db().groups().iterator().next());
   //!!!     contact.withGroup("test1");

        // create a new contact
        app.goTo().addNewPage();
        app.contact().create(contact, true);

        // number of contacts after creating a new one
       Contacts after = app.db().contacs();

        // check sizes
        assertThat(after.size(), equalTo(before.size() + 1));

        // compare lists
        assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
    }

}
