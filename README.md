# wallet
# Requirements
- A virtual wallet is used to access one or more transaction accounts. A user can have multiple transaction
accounts. For the sake of this tech challenge, a user will have a single wallet and all access to the transaction
account(s) needs to be provide by the wallet interface.
- The library should provide clear public endpoints to
  - Create a new wallet for a user
  - Return current account balance
  - Perform a withdrawal transaction on an account
  - Perform a deposit transaction on an account
  - Perform a transfer from one account to another account
  - Return last N transactions for an account
- A transaction is identified with a globally unique identifier and has a timestamp attribute indicating when the
transaction occurs.
- A withdrawal transaction is one that reduces the account balance with the given transaction amount.
- A deposit transaction is one that increases the account balance with the given transaction amount.
- At any given time the library should ensure that no further withdrawal transactions can be made if the account
balance goes to $0
- The library needs to handle all the operations correctly if the library is accessed in a concurrent manner. e.g.
multiple concurrent access to withdrawal, deposit & reversal transactions need to ensure that the user balance is
maintained correctly.
- Design and implement appropriate Java classes demonstrating good Object-Oriented Design principles.
- Define and implement appropriate exception classes.
- Include appropriate unit/integration tests coverage including any concurrent usage of library
