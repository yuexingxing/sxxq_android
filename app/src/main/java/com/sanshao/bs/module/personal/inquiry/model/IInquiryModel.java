package com.sanshao.bs.module.personal.inquiry.model;

import com.sanshao.bs.module.order.bean.OrderInfo;

import java.util.List;

public interface IInquiryModel {
    void returnInquiryList(List<OrderInfo> orderInfoList);

    void returnInquiryDetail(OrderInfo orderInfo);
}
