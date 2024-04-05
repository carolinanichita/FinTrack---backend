package com.bytebandits.fintrackbackend.service;

import com.bytebandits.fintrackbackend.dto.CalendarDayTransactionDTO;
import com.bytebandits.fintrackbackend.dto.TotalDTO;
import com.bytebandits.fintrackbackend.dto.TransactionDTO;
import com.bytebandits.fintrackbackend.model.Transaction;
import com.bytebandits.fintrackbackend.model.User;
import com.bytebandits.fintrackbackend.repository.AccountRepository;
import com.bytebandits.fintrackbackend.repository.CategoryRepository;
import com.bytebandits.fintrackbackend.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private LoginService userService;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    AccountRepository accountRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransactionService.class);

    @Autowired
    public TransactionService(TransactionRepository transactionRepository,
                              LoginService userService,
                              CategoryRepository categoryRepository,
                              AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.accountRepository = accountRepository;
    }

    public TransactionDTO saveTransaction(TransactionDTO transactionDTO) {
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transactionDTO.amount());
        newTransaction.setDescription(transactionDTO.description());
        newTransaction.setDate(transactionDTO.date());

        User user = userService.findUserById(transactionDTO.userId());

        if (user == null) {
            logger.error("User with ID " + transactionDTO.userId() + " not found.");
            return null;
        }
        newTransaction.setUser(user);
        categoryRepository.findById(transactionDTO.categoryId()).ifPresent(newTransaction::setCategory);
        accountRepository.findById(transactionDTO.accountId()).ifPresent(newTransaction::setAccount);

        Transaction savedTransaction = transactionRepository.save(newTransaction);

        logger.debug("Debug log message");
        logger.info("savedUserId = " + savedTransaction.getUser().getId());
        System.out.println("savedUserId = " + savedTransaction.getUser().getId());


        return getTransactionDTO(savedTransaction);
    }

    public List<TransactionDTO> getTransactionsYear(int year, UUID userId) {
        User user = userService.findUserById(userId);
        List<Transaction> transactionsForYear = transactionRepository.findTransactionsByUserAndYear(user.getId(), year);
        return getTransactionDTOList(transactionsForYear);
    }

    private List<TransactionDTO> getTransactionDTOList(List<Transaction> transactionsForYear) {
        return transactionsForYear.stream().map(expense -> getTransactionDTO(expense)).toList();
    }

    private TransactionDTO getTransactionDTO(Transaction transaction) {
        return new TransactionDTO(
                transaction.getAmount(),
                transaction.getDescription(),
                transaction.getDate(),
                transaction.getUser().getId(),
                transaction.getCategory().getId(),
                transaction.getAccount().getId()
        );
    }

    public List<TransactionDTO> getTransactionsYearAndMonth(int year, int month, UUID userId) {
        User user = userService.findUserById(userId);
        List<Transaction> transactionsForYear = transactionRepository.findTransactionsByUserAndYearAndMonth(year, month, user.getId());
        return getTransactionDTOList(transactionsForYear);
    }

    public List<TransactionDTO> getTransactionsTimePeriod(String startDate, String endDate, UUID userId) {
        User user = userService.findUserById(userId);
        LocalDate startLocalDate = LocalDate.parse(startDate);
        LocalDate endLocalDate = LocalDate.parse(endDate);
        List<Transaction> transactionsForYear = transactionRepository.findTransactionsByUserInTimePeriod(startLocalDate, endLocalDate, user.getId());
        return getTransactionDTOList(transactionsForYear);
    }

    public List<CalendarDayTransactionDTO> getCalendarTransactionsYearAndMonth(int year, int month, UUID userId) {
        User user = userService.findUserById(userId);
        List<CalendarDayTransactionDTO> calendarDayTransactions = transactionRepository.getCalendarDayTransactions(user.getId(), month, year);
        List<Object[]> results = transactionRepository.getTransactionsByDay(userId, month, year);

        Map<LocalDate, List<Transaction>> transactionsByDay = results.stream()
                .collect(Collectors.groupingBy(
                        tuple -> ((LocalDate) tuple[0]),
                        Collectors.mapping(tuple -> (Transaction) tuple[1], Collectors.toList())
                ));
        calendarDayTransactions.forEach(dto -> dto.setAllTransactions(getTransactionDTOList(transactionsByDay.get( dto.getTransactionDate()))));
        return calendarDayTransactions ;
    }

    public TotalDTO getTransactionsYearAndMonthTotal(int year, int month, UUID userId) {
        User user = userService.findUserById(userId);
        return transactionRepository.getTransactionsYearAndMonthTotal(user.getId(), month, year);
    }
}