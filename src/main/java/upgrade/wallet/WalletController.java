package upgrade.wallet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@RestController
public class WalletController {

    @Autowired
    @Qualifier("userRepository")
    UserRepository userRepository;

    @Autowired
    @Qualifier("transactionRepository")
    TransactionRepository transactionRepository;

//    UserMockedData userMockedData = UserMockedData.getInstance();

    @GetMapping("/users")
    public ResponseEntity index() {
        System.out.println("users find all");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userRepository.findAll());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        User user;

        user = userRepository.findByEmail(email);
        System.out.println(user.getFirstname() + " " + user.getLastname() + " " + user.getBalance());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(user);

    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserByID(@PathVariable String id) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        System.out.println(user.getFirstname() + " " + user.getLastname() + " " + user.getBalance());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(user);
    }

    @PostMapping("/user/{email}")
    public ResponseEntity createUser(@PathVariable String email, @RequestBody Map<String, String> body) {
        User user = new User(email, body.get("firstName"), body.get("lastName"), body.get("pass"), new HashSet<>(), Double.parseDouble(body.get("balance")));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userRepository.save(user));
    }

    @PutMapping("/user/{id}")
    public ResponseEntity updateUser(@PathVariable String id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        if (body.containsKey("firstName")) {
            user.setFirstname(body.get("firstName"));
        }
        if (body.containsKey("lastName")) {
            user.setLastname(body.get("lastName"));
        }
        if (body.containsKey("pass")) {
            user.setPassword(body.get("pass"));
        }
        User userSaved = userRepository.save(user);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userSaved);

    }

    @GetMapping("/getBalance/{id}")
    public ResponseEntity getBalance(@PathVariable String id) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        Map<String, Double> res = new HashMap<String, Double>() {
            {
                put("balance", user.getBalance());
            }
        };
        return ResponseEntity.accepted().headers(headers).body(res);
    }

    @PostMapping("/getNtransaction/{id}")
    public ResponseEntity getNtransactions(@PathVariable String id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        int n = Integer.parseInt(body.get("n"));
        List<Transaction> userTransactions = transactionRepository.findByUser(user).subList(0, n);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userTransactions);
    }

    @GetMapping("/transaction/{id}")
    public ResponseEntity getAllTransactions(@PathVariable String id) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        List<Transaction> userTransactions = transactionRepository.findByUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userTransactions);
    }

    @PostMapping("/transaction/{type}")
    public ResponseEntity transaction(@PathVariable String type, @RequestBody Map<String, String> body) {
        double amount = Double.parseDouble(body.get("amount"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        amount = type == "withdraw" ? amount * -1 : amount;

        User user = userRepository.findById(Integer.parseInt(body.get("id"))).get();
        if (type == "withdraw") {
            return ResponseEntity.accepted().headers(headers).body("can't withdraw from 0 balance");
        }
        Transaction transaction1 = transactionRepository.save(new Transaction(type, amount, user));
        user.setBalance(user.getBalance() + transaction1.getAmount());
        userRepository.save(user);


        return ResponseEntity.accepted().headers(headers).body(transaction1);
    }
}
