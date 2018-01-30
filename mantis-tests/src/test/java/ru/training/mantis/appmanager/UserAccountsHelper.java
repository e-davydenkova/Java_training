package ru.training.mantis.appmanager;

import org.openqa.selenium.By;

public class UserAccountsHelper extends HelperBase {

    public UserAccountsHelper(ApplicationManager app) {
        super(app);
    }

    public void resetPassword(int userId) {
        goToManageUsersPage();
        selectUserAndResetPassword(userId);
    }

    private void selectUserAndResetPassword(int userId) {
        wd.findElement(By.cssSelector(String.format("a[href='manage_user_edit_page.php?user_id=%s']", userId))).click();
        click(By.cssSelector("input[value='Reset Password']"));
    }

    public void goToManageUsersPage() {
        wd.findElement(By.cssSelector("a[href $='manage_overview_page.php']")).click();
        wd.findElement(By.cssSelector("a[href $='manage_user_page.php']")).click();
    }

    public void login(String username, String password) {
        wd.get(app.getProperty("web.baseUrl"));
        type(By.name("username"), username);
        type(By.name("password"), password);
        click(By.cssSelector("input[value='Login']"));
    }
}
