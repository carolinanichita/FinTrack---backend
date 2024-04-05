package com.bytebandits.fintrackbackend.repository;

import com.bytebandits.fintrackbackend.dto.CalendarDayTransactionDTO;
import com.bytebandits.fintrackbackend.dto.TotalDTO;
import com.bytebandits.fintrackbackend.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t " +
            "JOIN FETCH t.account " +
            "JOIN FETCH t.category " +
            "WHERE t.user.id = :userId " +
            "AND YEAR(t.date) = :year")
    List<Transaction> findTransactionsByUserAndYear(
            @Param("userId") UUID userId,
            @Param("year") int year
    );

    @Query("SELECT t FROM Transaction t " +
            "JOIN FETCH t.account " +
            "JOIN FETCH t.category " +
            "WHERE t.user.id = :userId " +
            "AND YEAR(t.date) = :year " +
            "AND MONTH(t.date) = :month")
    List<Transaction> findTransactionsByUserAndYearAndMonth(
            @Param("year") int year,
            @Param("month") int month,
            @Param("userId") UUID userId
    );

    @Query("SELECT t FROM Transaction t " +
            "JOIN FETCH t.account " +
            "JOIN FETCH t.category " +
            "WHERE t.user.id = :userId " +
            "AND t.date BETWEEN :startDate AND :endDate")
    List<Transaction> findTransactionsByUserInTimePeriod(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("userId") UUID userId
    );

    @Query("SELECT NEW com.bytebandits.fintrackbackend.dto.CalendarDayTransactionDTO(" +
            "t.date, " +
            "SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END), " +
            "SUM(CASE WHEN t.amount < 0 THEN t.amount ELSE 0 END), " +
            "SUM(t.amount)) " +
            "FROM Transaction t " +
            "JOIN t.account a " +
            "JOIN t.category c " +
            "WHERE MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year " +
            "AND t.user.id = :userId " +
            "GROUP BY t.date")
    List<CalendarDayTransactionDTO> getCalendarDayTransactions(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT t.date, t " +
            "FROM Transaction t " +
            "JOIN FETCH t.account " +
            "JOIN FETCH t.category " +
            "WHERE MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year " +
            "AND t.user.id = :userId " +
            "ORDER BY t.date")
    List<Object[]> getTransactionsByDay(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") int year
    );

    @Query("SELECT NEW com.bytebandits.fintrackbackend.dto.TotalDTO(" +
            "COALESCE(CAST(SUM(CASE WHEN t.amount > 0 THEN t.amount ELSE 0 END) AS INTEGER), 0), " +
            "COALESCE(CAST(SUM(CASE WHEN t.amount < 0 THEN t.amount ELSE 0 END) AS INTEGER), 0), " +
            "COALESCE(CAST(SUM(t.amount) AS INTEGER), 0)) " +
            "FROM Transaction t " +
            "WHERE MONTH(t.date) = :month " +
            "AND YEAR(t.date) = :year " +
            "AND t.user.id = :userId ")
    TotalDTO getTransactionsYearAndMonthTotal(
            @Param("userId") UUID userId,
            @Param("month") int month,
            @Param("year") int year
    );
}