package com.mindhub.homebanking;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.models.CardColor.*;
import static com.mindhub.homebanking.models.TransactionType.CREDIT;
import static com.mindhub.homebanking.models.TransactionType.DEBIT;

@SpringBootApplication
public class HomebankingApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository,
																		TransactionRepository transactionRepository , LoanRepository loanRepository,
																		ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
			Client melbaClient = new Client("Melba", "Morel", "melba@mindhub.com", passwordEncoder.encode("melba123"));
			Account accountOneMelba = new Account("VIN001", LocalDateTime.now(), 5000.0);
			Account accountTwoMelba = new Account("VIN002", LocalDateTime.now().plusDays(1), 7500.0);
			clientRepository.save(melbaClient);
			melbaClient.addAccounts(accountOneMelba);
			melbaClient.addAccounts(accountTwoMelba);
			accountRepository.save(accountOneMelba);
			accountRepository.save(accountTwoMelba);
			//Creación de transacciones
			Transaction oneTransMelba = new Transaction(CREDIT, 15000.0, LocalDateTime.now(), "Extra Salary",accountOneMelba);
			Transaction twoTransMelba = new Transaction(DEBIT, 3000.0, LocalDateTime.now(), "Grocery Shopping",accountOneMelba);
			Transaction threeTransMelba = new Transaction(DEBIT, 1000.0, LocalDateTime.now(), "Hairdresser Payment ",accountOneMelba);
			Transaction fourTransMelba = new Transaction(CREDIT, 8000.0, LocalDateTime.now(), "Debt Payment",accountTwoMelba);
			Transaction fiveTransMelba = new Transaction(DEBIT, 2000.0, LocalDateTime.now(), "Clothing Store",accountTwoMelba);
			Transaction sixTransMelba = new Transaction(DEBIT, 1000.0, LocalDateTime.now(), "Air Ticket Purchase",accountTwoMelba);

			accountOneMelba.addTransactions(oneTransMelba);
			accountOneMelba.addTransactions(twoTransMelba);
			accountOneMelba.addTransactions(threeTransMelba);
			accountTwoMelba.addTransactions(fourTransMelba);
			accountTwoMelba.addTransactions(fiveTransMelba);
			accountTwoMelba.addTransactions(sixTransMelba);
			transactionRepository.save(oneTransMelba);
			transactionRepository.save(twoTransMelba);
			transactionRepository.save(threeTransMelba);
			transactionRepository.save(fourTransMelba);
			transactionRepository.save(fiveTransMelba);
			transactionRepository.save(sixTransMelba);
			//creación de prestamos

			Loan mortgageLoan = new Loan("Mortgage", 500000.0, List.of(12, 24, 36, 48, 60));
			Loan personalLoan = new Loan("Personal", 100000.0, List.of(6, 12, 24));
			Loan autoLoan = new Loan("Auto", 300000.0, List.of(6, 12, 24, 36));
			loanRepository.save(mortgageLoan);
			loanRepository.save(personalLoan);
			loanRepository.save(autoLoan);
			// Creación prestamos cliente melba
			ClientLoan firstLoanMelba = new ClientLoan(400000.0, 60, melbaClient, mortgageLoan);
			ClientLoan secondLoanMelba = new ClientLoan(50000.0, 12, melbaClient, personalLoan);
			clientLoanRepository.save(firstLoanMelba);
			clientLoanRepository.save(secondLoanMelba);

			Card card1= new Card(melbaClient.getFirstName()+" "+ melbaClient.getLastName(),CardType.DEBIT,GOLD,"1456-1573-5678-5678",180, LocalDate.now(),LocalDate.now().plusYears(5),melbaClient);
			Card card2= new Card(melbaClient.getFirstName()+" "+ melbaClient.getLastName(),CardType.DEBIT,SILVER,"2456-1573-2278-5678",180, LocalDate.now(),LocalDate.now().plusYears(5),melbaClient);
			Card card3= new Card(melbaClient.getFirstName()+" "+melbaClient.getLastName(),CardType.CREDIT,TITANIUM,"2563-1489-8690-5678",623,LocalDate.now(),LocalDate.now().plusYears(5),melbaClient);
			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

			// creación de cliente 2 con sus cuentas, transacciones y prestamos

			Client pabloClient = new Client("Pablo", "Rodriguez", "pablo@mindhub.com", passwordEncoder.encode("pablo123"));
			Account accountOnePablo = new Account("VIN003", LocalDateTime.now(), 1000.0);
			Account accountTwoPablo = new Account("VIN004", LocalDateTime.now().plusDays(1), 3500.0);
			clientRepository.save(pabloClient);
			pabloClient.addAccounts(accountOnePablo);
			pabloClient.addAccounts(accountTwoPablo);
			accountRepository.save(accountOnePablo);
			accountRepository.save(accountTwoPablo);
			Transaction oneTransPablo = new Transaction(CREDIT, 8000.0, LocalDateTime.now(), "Debt Payment",accountOnePablo);
			Transaction twoTransPablo = new Transaction(DEBIT, 2000.0, LocalDateTime.now(), "Clothing Store",accountOnePablo);
			Transaction threeTransPablo = new Transaction(DEBIT, 1000.0, LocalDateTime.now(), "Air Ticket Purchase",accountTwoPablo);
			accountOnePablo.addTransactions(oneTransPablo);
			accountOnePablo.addTransactions(twoTransPablo);
			accountTwoPablo.addTransactions(threeTransPablo);
			transactionRepository.save(oneTransPablo);
			transactionRepository.save(twoTransPablo);
			transactionRepository.save(threeTransPablo);

			ClientLoan firstLoanPablo = new ClientLoan(100000.0, 24, pabloClient, personalLoan);
			ClientLoan secondLoanPablo = new ClientLoan(200000.0, 36, pabloClient, autoLoan);
			clientLoanRepository.save(firstLoanPablo);
			clientLoanRepository.save(secondLoanPablo);

			Client admin = new Client("admin", "admin", "admin@admin.com", passwordEncoder.encode("admin"));
			clientRepository.save(admin);
		};}



}
