package ru.training.mantis.tests;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.training.mantis.model.MailMessage;
import ru.training.mantis.model.UserData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase {

    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws IOException, MessagingException {
        UserData user = new UserData();
        String password = "password111";

        // select user to reset password from exiting users in db
        List<UserData> users = app.db().users();
        for (UserData u : users) {
            if (u.getEmail().contains("@localhost.localdomain")) {
                user = u;
                break;
            }
        }
        Assert.assertFalse(user.getId() == 0, "No users with email ending to @localhost.localdomain in db");

        app.accounts().login("administrator", "root");
        app.accounts().resetPassword(user.getId());

        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String confirmationLink = app.mail().findConfirmationLink(mailMessages, user.getEmail());
        app.registration().finish(confirmationLink, password);
        assertTrue(app.newSession().login(user.getName(), password));
    }


    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
