package cn.sanshaoxingqiu.ssbm.module.live.bean;

import android.text.TextUtils;

import com.tencent.bugly.Bugly;

public class LiveApplyResponse {

    public interface AuditStatus {
        String UNAPPLY = "UNAPPLY";//未申请
        String AUDIT = "AUDIT";//待审核
        String SUCCESS = "SUCCESS";//审核通过
        String FAILED = "FAILED";//审核通不过
    }

    public String anchor_name;
    public String identity_number;
    public String identity_card_front;
    public String identity_card_back;
    public String identity_handle;
    public String audit_status;
    public String live_status;
    public String status;//开播权限状态主播状态（1:正常 2:处罚）
    public String reason;
    public String frontcover;

    /**
     * 是否允许直播
     *
     * @return
     */
    public boolean isAllowLive() {
        return TextUtils.equals("1", status);
    }

    public boolean isAuditSuccess() {
        return TextUtils.equals(AuditStatus.SUCCESS, audit_status);
    }

    public boolean isAuditFailed() {
        return TextUtils.equals(AuditStatus.FAILED, audit_status);
    }

    public boolean isAuditing() {
        return TextUtils.equals(AuditStatus.AUDIT, audit_status);
    }

    public boolean isUnApply() {
        return TextUtils.equals(AuditStatus.UNAPPLY, audit_status);
    }
}
