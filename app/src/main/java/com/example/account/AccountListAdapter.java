package com.example.account;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.MyViewHolder> {
    private List<Account> myDataset;

    public interface OnItemClickListener {
        void onItemClick(Account account);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView type, amount, time, remarks;

        public MyViewHolder(View view) {
            super(view);
            type = (TextView) view.findViewById(R.id.type);
            amount = (TextView) view.findViewById(R.id.amount);
            time = (TextView) view.findViewById(R.id.time);
            remarks = (TextView) view.findViewById(R.id.remarks);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        Account account = myDataset.get(position);
                        Intent intent = new Intent(v.getContext(), AccountEditActivity.class);
                        intent.putExtra("accountId", account.getId());
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    public AccountListAdapter(List<Account> myDataset) {
        this.myDataset = myDataset;
    }

    @Override
    public AccountListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_display, parent, false);
        return new MyViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Account account = myDataset.get(position);
        holder.type.setText("分类: " + account.getType());

        // 根据类型判断是否在金额前添加负号
        if (shouldAddNegativeSign(account.getType())) {
            holder.amount.setText("金额: -" + account.getAmount());
        } else {
            holder.amount.setText("金额: " + account.getAmount());
        }
        // 将日期转换为指定格式的字符串
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(account.getTime());
        holder.time.setText("时间: " + formattedDate);
        holder.remarks.setText("备注: " + account.getRemarks());

        // 判断类型是否为购物，设置背景颜色
        switch (account.getType()) {
            case "餐饮":
                setBackgroundColorByRGB(holder.itemView, 255, 124, 121);
                break;
            case "购物":
                setBackgroundColorByRGB(holder.itemView, 254, 196, 120);
                break;
            case "交通":
                setBackgroundColorByRGB(holder.itemView, 249, 152, 126);
                break;
            case "运动":
                setBackgroundColorByRGB(holder.itemView, 240, 244, 131);
                break;
            case "娱乐":
                setBackgroundColorByRGB(holder.itemView, 172, 217, 158);
                break;
            case "学习":
                setBackgroundColorByRGB(holder.itemView, 125, 249, 140);
                break;
            case "办公":
                setBackgroundColorByRGB(holder.itemView, 133, 228, 242);
                break;
            case "工资":
                setBackgroundColorByRGB(holder.itemView, 128, 137, 246);
                break;
            case "兼职":
                setBackgroundColorByRGB(holder.itemView, 169, 154, 221);
                break;
            case "理财":
                setBackgroundColorByRGB(holder.itemView, 232, 142, 155);
                break;
        }
    }

    public void setAccounts(List<Account> accounts) {
        this.myDataset = accounts;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myDataset.size();
    }

    // 判断是否应该添加负号
    private boolean shouldAddNegativeSign(String type) {
        String[] types = {"餐饮", "购物", "交通", "运动", "娱乐", "学习", "办公"};
        for (String t : types) {
            if (t.equals(type)) {
                return true;
            }
        }
        return false;
    }

    //设置颜色RGB值
    private void setBackgroundColorByRGB(View view, int red, int green, int blue) {
        int color = Color.rgb(red, green, blue);
        view.setBackgroundColor(color);
    }
}
