package com.example.account;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button buttonAdd;
    private AccountListAdapter accountListAdapter;
    private RecyclerView recyclerView;
    private AccountViewModel accountViewModel;
    private double totalAmount = 0.0;
    private double totalIncome = 0.0;
    private double totalExpenditure = 0.0;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonAdd = findViewById(R.id.btn_add);
        // 修改总金额的TextView文本值
        TextView tvTotalAmount = findViewById(R.id.tv_total_amount);
        TextView tvIncome = findViewById(R.id.tv_income);
        TextView tvExpenditure = findViewById(R.id.tv_expenditure);
        buttonAdd.setOnClickListener(new View.OnClickListener() {    //监听器setOnClickListener,实现View.OnClickListener接口
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AccountEditActivity.class);
                startActivity(intent);
            }
        });

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.spinner_option, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //组件垂直往下,只有一组
        accountListAdapter = new AccountListAdapter(new ArrayList<>());
        recyclerView.setAdapter(accountListAdapter);
        accountListAdapter.setOnItemClickListener(new AccountListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Account account) {
                Intent intent = new Intent(MainActivity.this, AccountEditActivity.class);
                intent.putExtra("accountId", account.getId()); // 当我点击已经存在于列表的项需要传递点击的记录的id属性
                startActivity(intent);
            }
        });

        //初始化accountViewModel
        accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);

        accountViewModel.getAllAccounts().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> accounts) {
                // 重新计算总金额
                totalAmount = 0.0;
                totalIncome = 0.0;
                totalExpenditure = 0.0;

                //计算totalIncome收入和totalExpenditure支出
                for (Account account : accounts) {
                    if (account.getType().equals("工资") || account.getType().equals("兼职") || account.getType().equals("理财")) {
                        totalIncome += account.getAmount();
                    }
                }
                for (Account account : accounts) {
                    if (account.getType().equals("餐饮") || account.getType().equals("购物") || account.getType().equals("交通") || account.getType().equals("运动")
                            || account.getType().equals("娱乐") || account.getType().equals("学习") || account.getType().equals("办公")) {
                        totalExpenditure += account.getAmount();
                    }
                }
                //计算总金额
                totalAmount = totalIncome - totalExpenditure;

                // 格式化金额到两位小数
                String formattedIncome = String.format("%.2f", totalIncome);
                String formattedExpenditure = String.format("%.2f", totalExpenditure);
                String formattedTotalAmount = String.format("%.2f", totalAmount);
                // 修改3个金额的TextView文本值
                tvIncome.setText( "收入："+ formattedIncome);
                tvExpenditure.setText("支出：" + formattedExpenditure);
                tvTotalAmount.setText("总金额：" + formattedTotalAmount);
                accountListAdapter.setAccounts(accounts);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selectedType = (String) parent.getItemAtPosition(pos);
                LiveData<List<Account>> accountsByType;
                //选时间倒序排序,收入会显示收入的所有记录,支出会显示所有支出记录
                switch (selectedType) {
                    case "时间":
                        accountsByType = accountViewModel.getAccountsByTimeDesc();
                        break;
                    case "收入":
                        accountsByType = accountViewModel.getIncomeAccounts();
                        break;
                    case "支出":
                        accountsByType = accountViewModel.getExpenditureAccounts();
                        break;
                    case "所有":
                        accountsByType = accountViewModel.getAllAccounts();
                        break;
                    default:
                        accountsByType = accountViewModel.getAccountsByType(selectedType);
                }

                accountsByType.observe(MainActivity.this, new Observer<List<Account>>() {
                    @Override
                    public void onChanged(@Nullable final List<Account> accounts) {
                        // 修改Adapter的数据，而不是创建新的Adapter
                        accountListAdapter.setAccounts(accounts);
                        // 通知Adapter数据已经改变，让它更新视图
                        accountListAdapter.notifyDataSetChanged();
                    }
                });
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }
}