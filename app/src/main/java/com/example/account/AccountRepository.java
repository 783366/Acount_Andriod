package com.example.account;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AccountRepository {
    private AccountDao mAccountDao;
    private LiveData<List<Account>> mAllAccounts;

    AccountRepository(Application application) {
        AccountRoomDatabase db = AccountRoomDatabase.getDatabase(application);
        mAccountDao = db.accountDao();
        mAllAccounts = mAccountDao.getAllAccounts();
    }

    LiveData<List<Account>> getAllAccounts() {
        return mAllAccounts;
    }

    public void insert(Account account) {
        new insertAsyncTask(mAccountDao).execute(account);
    }

    public LiveData<Account> getAccount(int id) {
        return mAccountDao.getAccount(id);
    }

    private static class insertAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao mAsyncTaskDao;

        insertAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void delete(Account account) {
        new deleteAsyncTask(mAccountDao).execute(account);
    }

    private static class deleteAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao mAsyncTaskDao;

        deleteAsyncTask(AccountDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Account... params) {
            mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }

    public void update(Account account) {
        new UpdateAccountTask(mAccountDao).execute(account);
    }

    // 使用 AsyncTask 处理数据库操作
    private static class UpdateAccountTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        UpdateAccountTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.update(accounts[0]);
            return null;
        }
    }

    LiveData<List<Account>> getAccountsByType(String type) {
        return mAccountDao.getAccountsByType(type);
    }

    LiveData<List<Account>> getIncomeAccounts() {
        return mAccountDao.getIncomeAccounts();
    }

    LiveData<List<Account>> getExpenditureAccounts() {
        return mAccountDao.getExpenditureAccounts();
    }

    LiveData<List<Account>> getAccountsByTimeDesc() {
        return mAccountDao.getAccountsByTimeDesc();
    }
}
