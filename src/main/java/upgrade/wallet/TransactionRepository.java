package upgrade.wallet;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Set<Transaction> findByUser_Id(int id);

//    List<Transaction> findByUser_id_Id(int id);

    List<Transaction> findByUser(User user);
}
