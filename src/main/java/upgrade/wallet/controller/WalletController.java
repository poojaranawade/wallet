package upgrade.wallet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import upgrade.wallet.helper.UserService;
import upgrade.wallet.model.Transaction;
import upgrade.wallet.model.User;
import upgrade.wallet.repository.TransactionRepository;
import upgrade.wallet.repository.UserRepository;

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

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("resource/templateslogin");
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity createNewUser(@RequestBody Map<String, String> body) {
        User userExists = userService.findUserByEmail(body.get("email"));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        if (userExists != null) {
            Map<String, String> res = new HashMap<String, String>() {
                {
                    put("Error", "Email already in use");
                }
            };
            return ResponseEntity.accepted().headers(headers).body(res);
        } else {
            User user = new User(body.get("email"), body.get("firstName"), body.get("lastName"), body.get("pass"), new HashSet<>(), Double.parseDouble(body.get("balance")));
            userService.saveUser(user);
            return ResponseEntity.accepted().headers(headers).body(user);

        }
    }

    @GetMapping("/users")
    public ResponseEntity index() {
        System.out.println("users find all");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userRepository.findAll());
    }

    @GetMapping("/user/email/{email}")
    public ResponseEntity getUserByEmail(@PathVariable String email) {
        User user;

        user = userRepository.findByEmail(email);
        System.out.println(user.getFirstname() + " " + user.getLastname() + " " + user.getBalance());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(user);

    }

    @GetMapping("/user/id/{id}")
    public ResponseEntity getUserByID(@PathVariable String id) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        System.out.println(user.getFirstname() + " " + user.getLastname() + " " + user.getBalance());

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(user);
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

    @GetMapping("/user/getBalance/{id}")
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

    @PostMapping("/user/getNtransaction/{id}")
    public ResponseEntity getNtransactions(@PathVariable String id, @RequestBody Map<String, String> body) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        int n = Integer.parseInt(body.get("n"));
        List<Transaction> userTransactions = transactionRepository.findByUser(user).subList(0, n);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userTransactions);
    }

    @GetMapping("/user/transaction/{id}")
    public ResponseEntity getAllTransactions(@PathVariable String id) {
        User user = userRepository.findById(Integer.parseInt(id)).get();
        List<Transaction> userTransactions = transactionRepository.findByUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Responded", "WallerController");
        return ResponseEntity.accepted().headers(headers).body(userTransactions);
    }

    @PostMapping("/user/transaction/{type}")
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
