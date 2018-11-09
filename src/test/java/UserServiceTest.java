
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import upgrade.wallet.helper.UserService;
import upgrade.wallet.model.User;
import upgrade.wallet.repository.TransactionRepository;
import upgrade.wallet.repository.UserRepository;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;
    @Mock
    private TransactionRepository mockTransactionRepository;
    @Mock
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;

    private UserService userServiceUnderTest;
    private User user;

    @Before
    public void setUp() {
        initMocks(this);
        userServiceUnderTest = new UserService(mockUserRepository,
                mockTransactionRepository,
                mockBCryptPasswordEncoder);

        userServiceUnderTest.saveUser(new User("test@test.com", "fname", "lname", "pass", new HashSet<>(), 10000.0));
    }

    @Test
    public void testFindUserByEmail() {
        // Setup
        final String email = "test@test.com";

        // Run the test
        final User result = userServiceUnderTest.findUserByEmail(email);

        // Verify the results
        assertEquals(email, result.getEmail());
    }
}
