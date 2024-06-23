package com.example.account;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository mRepository;
    private LiveData<List<Account>> mAllAccounts;

    public AccountViewModel(Application application) {
        super(application);
        mRepository = new AccountRepository(application);
        mAllAccounts = mRepository.getAllAccounts();
    }
    LiveData<List<Account>> getAllAccounts() {
        return mAllAccounts;
    }
    public void insert(Account account) {
        mRepository.insert(account);
    }
    public LiveData<Account> getAccount(int id) {
        return mRepository.getAccount(id);
    }
    public void delete(Account account) {
        mRepository.delete(account);
    }

    public void update(Account account) {
        mRepository.update(account);
    }

    LiveData<List<Account>> getAccountsByType(String type) {
        return mRepository.getAccountsByType(type);
    }

    LiveData<List<Account>> getIncomeAccounts() {
        return mRepository.getIncomeAccounts();
    }

    LiveData<List<Account>> getExpenditureAccounts() {
        return mRepository.getExpenditureAccounts();
    }

    LiveData<List<Account>> getAccountsByTimeDesc() {
        return mRepository.getAccountsByTimeDesc();
    }
}