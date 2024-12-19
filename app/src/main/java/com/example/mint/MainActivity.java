package com.example.mint;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private BillAdapter billAdapter;
    private List<Bill> billList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化 RecyclerView 和数据
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        billList = new ArrayList<>();
        billAdapter = new BillAdapter(billList, position -> {
            // 长按删除操作
            deleteBill(position);
        }, position -> {
            // 点击编辑操作
            editBill(position);
        });
        recyclerView.setAdapter(billAdapter);

        // 添加账单按钮的点击事件
        findViewById(R.id.addButton).setOnClickListener(v -> showAddBillDialog());
    }

    // 删除账单
    private void deleteBill(int position) {
        Bill billToRemove = billList.get(position);

        new AlertDialog.Builder(this)
                .setTitle("确认删除")
                .setMessage("是否确认删除账单：" + billToRemove.getName() + "？")
                .setPositiveButton("删除", (dialog, which) -> {
                    billList.remove(position);
                    billAdapter.notifyItemRemoved(position);
                    Toast.makeText(MainActivity.this, "账单已删除", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("取消", null)
                .show();
    }

    // 编辑账单
    private void editBill(int position) {
        Bill billToEdit = billList.get(position);

        // 创建编辑对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("编辑账单");

        // 创建布局，包含三个输入框：名称、金额和日期
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_bill, null);
        final EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        final EditText amountEditText = dialogView.findViewById(R.id.amountEditText);
        final EditText dateEditText = dialogView.findViewById(R.id.dateEditText);

        // 预填充当前账单数据
        nameEditText.setText(billToEdit.getName());
        amountEditText.setText(String.valueOf(billToEdit.getAmount()));
        dateEditText.setText(billToEdit.getDate());

        builder.setView(dialogView);

        builder.setPositiveButton("保存", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            String amountStr = amountEditText.getText().toString();
            String date = dateEditText.getText().toString();

            // 验证输入
            if (name.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
                Toast.makeText(MainActivity.this, "所有字段不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                // 更新账单
                billToEdit.setName(name);
                billToEdit.setAmount(amount);
                billToEdit.setDate(date);

                billAdapter.notifyItemChanged(position); // 更新 RecyclerView
                Toast.makeText(MainActivity.this, "账单已更新", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "金额格式不正确", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", null);

        builder.show();
    }

    // 显示添加账单的对话框
    private void showAddBillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("添加账单");

        // 创建布局，包含三个输入框：名称、金额和日期
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_bill, null);
        final EditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        final EditText amountEditText = dialogView.findViewById(R.id.amountEditText);
        final EditText dateEditText = dialogView.findViewById(R.id.dateEditText);

        builder.setView(dialogView);

        builder.setPositiveButton("添加", (dialog, which) -> {
            String name = nameEditText.getText().toString();
            String amountStr = amountEditText.getText().toString();
            String date = dateEditText.getText().toString();

            // 验证输入
            if (name.isEmpty() || amountStr.isEmpty() || date.isEmpty()) {
                Toast.makeText(MainActivity.this, "所有字段不能为空", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                Bill newBill = new Bill(name, amount, date);
                billList.add(newBill);
                billAdapter.notifyDataSetChanged(); // 更新 RecyclerView
                Toast.makeText(MainActivity.this, "账单已添加", Toast.LENGTH_SHORT).show();
            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "金额格式不正确", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("取消", null);

        builder.show();
    }
}
