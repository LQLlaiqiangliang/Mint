package com.example.mint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.BillViewHolder> {
    private List<Bill> billList;
    private OnItemLongClickListener longClickListener;
    private OnItemClickListener clickListener;

    // 构造函数
    public BillAdapter(List<Bill> billList, OnItemLongClickListener longClickListener, OnItemClickListener clickListener) {
        this.billList = billList;
        this.longClickListener = longClickListener;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 创建每个条目的布局视图
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        // 获取账单数据并绑定到视图
        Bill bill = billList.get(position);
        holder.nameTextView.setText(bill.getName());
        holder.amountTextView.setText(String.format("$%.2f", bill.getAmount()));

        // 设置长按监听器
        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onItemLongClick(position); // 通知长按事件
                return true; // 表示事件已处理
            }
            return false;
        });

        // 设置点击监听器
        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) {
                clickListener.onItemClick(position); // 通知点击事件
            }
        });
    }

    @Override
    public int getItemCount() {
        return billList.size(); // 返回账单的数量
    }

    // 自定义 ViewHolder
    public static class BillViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView amountTextView;

        public BillViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(android.R.id.text1);
            amountTextView = itemView.findViewById(android.R.id.text2);
        }
    }

    // 定义一个接口，用于响应长按事件
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    // 定义一个接口，用于响应点击事件
    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
