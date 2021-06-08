package com.house.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.house.config.AlipayConfig;
import com.house.pojo.entity.Order;
import com.house.pojo.entity.VerifyMessage;
import com.house.service.IAlipayService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Service
public class AlipayServiceImpl implements IAlipayService {
    /**
     * 支付
     *
     * @param order
     * @return
     */
    @Override
    public String pay(Order order) {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = order.getOrderNumber();
        //付款金额，必填
        String total_amount = order.getOrderPrice().toString();
        //订单名称，必填
        String subject = order.getOrderName();

        //设置请求内容
        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
        model.setOutTradeNo(out_trade_no);
        model.setTotalAmount(total_amount);
        model.setSubject(subject);
        model.setProductCode("FAST_INSTANT_TRADE_PAY");
        alipayRequest.setBizModel(model);

        try {
            //执行请求
            String result = alipayClient.pageExecute(alipayRequest).getBody();
            return result;
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return "FAIL";
    }

    /**
     * 验证签名
     *
     * @return
     */
    @Override
    public VerifyMessage verify(HttpServletRequest request, String orderId) {
        Map<String, String> params = new HashMap<>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            try {
                //乱码解决，这段代码在出现乱码时使用
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        boolean signVerified = false; //调用SDK验证签名
        try {
            signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset, AlipayConfig.sign_type);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        //——请在这里编写您的程序（以下代码仅作参考）——
        VerifyMessage message = new VerifyMessage();
        //验签
        if (signVerified) {
            message.setMessage("OK");
            message.setOrderId(Integer.parseInt(orderId));    //获取支付的订单id
            return message;
        } else {
            message.setMessage("FAIL");
            return message;
        }
    }
}
