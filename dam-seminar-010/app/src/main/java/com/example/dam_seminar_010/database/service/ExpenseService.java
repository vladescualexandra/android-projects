package com.example.dam_seminar_010.database.service;

import android.content.Context;

import com.example.dam_seminar_010.async.AsyncTaskRunner;
import com.example.dam_seminar_010.async.Callback;
import com.example.dam_seminar_010.database.DatabaseManager;
import com.example.dam_seminar_010.database.dao.ExpenseDao;
import com.example.dam_seminar_010.database.model.Expense;

import java.util.List;
import java.util.concurrent.Callable;

public class ExpenseService {

    private final ExpenseDao expenseDao;
    private final AsyncTaskRunner taskRunner;

    public ExpenseService(Context context) {
        expenseDao = DatabaseManager.getInstance(context).getExpenseDao();
        taskRunner = new AsyncTaskRunner();
    }

    public List<Expense> getAllWithoutCallback() {
        return expenseDao.getAll();
    }

    public void getAll(Callback<List<Expense>> callback) {
        Callable<List<Expense>> callable = new Callable<List<Expense>>() {
            @Override
            public List<Expense> call() throws Exception {
                return expenseDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Expense> callback, final Expense expense) {
        Callable<Expense> callable = new Callable<Expense>() {
            @Override
            public Expense call() throws Exception {
                if (expense == null) {
                    return null;
                } else {
                    long id = expenseDao.insert(expense);
                    if (id == -1) {
                        return null;
                    }
                    expense.setId(id);
                    return expense;
                }
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Expense> callback, final Expense expense) {
        Callable<Expense> callable = new Callable<Expense>() {
            @Override
            public Expense call() throws Exception {
                if (expense == null) {
                    return null;
                }
                int count = expenseDao.update(expense);
                if (count < 1) {
                    return null;
                }
                return expense;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Expense expense) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                if (expense == null) {
                    return -1;
                }
                return expenseDao.delete(expense);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }
}
