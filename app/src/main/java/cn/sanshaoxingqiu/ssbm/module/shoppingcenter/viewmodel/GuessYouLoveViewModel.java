package cn.sanshaoxingqiu.ssbm.module.shoppingcenter.viewmodel;

import com.exam.commonbiz.base.BaseViewModel;
import com.exam.commonbiz.net.BaseResponse;
import com.exam.commonbiz.net.OnLoadListener;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.bean.GoodsDetailInfo;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.IGuessYouLoveModel;
import cn.sanshaoxingqiu.ssbm.module.shoppingcenter.model.ShoppingCenterModel;

import java.util.List;

/**
 * @Author yuexingxing
 * @time 2020/6/18
 */
public class GuessYouLoveViewModel extends BaseViewModel {
    private String TAG = GuessYouLoveViewModel.class.getSimpleName();
    private IGuessYouLoveModel mCallBack;

    public void setCallBack(IGuessYouLoveModel guessYouLoveModel) {
        mCallBack = guessYouLoveModel;
    }

    public void getGuessYouLoveData() {

        ShoppingCenterModel.getGuessYouLoveData(new OnLoadListener<List<GoodsDetailInfo>>() {

            @Override
            public void onLoadStart() {

            }

            @Override
            public void onLoadCompleted() {

            }

            @Override
            public void onLoadSucessed(BaseResponse<List<GoodsDetailInfo>> t) {
                if (mCallBack != null) {
                   mCallBack.returnGuessYouLoveData(t.getContent());
                }
            }

            @Override
            public void onLoadFailed(String errMsg) {
                if (mCallBack != null) {
                    mCallBack.returnGuessYouLoveData(null);
                }
            }
        });
    }
}
