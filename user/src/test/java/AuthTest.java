import com.pechen.user.UsersContainer;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * Created by pechen on 7/22/2017.
 */
public class AuthTest {

    @Test
    public void test() {
        String username1 = "username";
        String username2 = "123";
        String username3 = "#5gv";

        UsersContainer usersContainer = new UsersContainer();

        assertEquals(true, usersContainer.authNewUser(username1));
        assertEquals(true, usersContainer.authNewUser(username2));
        assertEquals(true,usersContainer.authNewUser(username3));
        assertEquals(false,usersContainer.authNewUser(username1));
    }
}
