package cn.sanshaoxingqiu.ssbm.module.personal.income.view.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exam.commonbiz.util.ContainerUtil;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import cn.sanshaoxingqiu.ssbm.R;
import cn.sanshaoxingqiu.ssbm.module.personal.income.bean.BankCardInfo;
import cn.sanshaoxingqiu.ssbm.module.personal.income.view.adapter.BankCardAdapter;

/**
 * 选择银行卡
 *
 * @Author yuexingxing
 * @time 2020/10/12
 */
public class SelectBankCardDialog {

    private BankCardAdapter mBankCardAdapter;
    private ItemClickListener mItemClickListener;

    public interface ItemClickListener {
        void addNewBankCard();

        void onItemClick(BankCardInfo bankCardInfo);
    }

    public void setItemClickListener(ItemClickListener listener) {
        mItemClickListener = listener;
    }

    public void show(Context context, List<BankCardInfo> bankCardInfoList) {
        LinearLayout layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.dialog_layout_select_bank_card, null);
        RecyclerView recyclerView = layout.findViewById(R.id.recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        mBankCardAdapter = new BankCardAdapter();
        recyclerView.setAdapter(mBankCardAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        mBankCardAdapter.addData(bankCardInfoList);
        mBankCardAdapter.setOnItemClickListener(new BankCardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, BankCardInfo bankCardInfo) {
                initData(position);
            }
        });

        BottomSheetDialog dialog = new BottomSheetDialog(context, R.style.BottomSheetDialog);
        dialog.setContentView(layout);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.show();

        layout.findViewById(R.id.ll_add_new_bankcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (mItemClickListener != null) {
                    mItemClickListener.addNewBankCard();
                }
            }
        });

        layout.findViewById(R.id.tv_ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(checkBankInfo(bankCardInfoList));
                }
            }
        });
    }

    private void initData(int position) {
        if (mBankCardAdapter == null || ContainerUtil.isEmpty(mBankCardAdapter.getData())) {
            return;
        }

        for (int i = 0; i < mBankCardAdapter.getData().size(); i++) {
            mBankCardAdapter.getData().get(i).checked = false;
            if (i == position) {
                mBankCardAdapter.getData().get(i).checked = true;
            }
        }
        mBankCardAdapter.notifyDataSetChanged();
    }

    private BankCardInfo checkBankInfo(List<BankCardInfo> bankCardInfoList) {
        if (ContainerUtil.isEmpty(bankCardInfoList)) {
            return null;
        }

        BankCardInfo bankCardInfo = null;
        for (int i = 0; i < bankCardInfoList.size(); i++) {
            if (bankCardInfoList.get(i).checked) {
                bankCardInfo = bankCardInfoList.get(i);
                break;
            }
        }
        return bankCardInfo;
    }
}
