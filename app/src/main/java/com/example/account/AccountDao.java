package com.example.account;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insert(Account account);

    @Delete
    void delete(Account account);

    @Update
    void update(Account account);

    @Query("SELECT * FROM account_table")
    LiveData<List<Account>> getAllAccounts();

    @Query("SELECT * FROM account_table WHERE id = :id")
    LiveData<Account> getAccount(int id);

    @Query("SELECT * FROM account_table WHERE type = :type")
    LiveData<List<Account>> getAccountsByType(String type);

    @Query("SELECT * FROM account_table WHERE type IN ('工资', '兼职', '理财')")
    LiveData<List<Account>> getIncomeAccounts();

    @Query("SELECT * FROM account_table WHERE type IN ('餐饮', '购物', '交通', '运动', '娱乐', '学习', '办公')")
    LiveData<List<Account>> getExpenditureAccounts();

    @Query("SELECT * FROM account_table ORDER BY time DESC")
    LiveData<List<Account>> getAccountsByTimeDesc();
}
