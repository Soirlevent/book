package com.ruoyi.tools.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 支付宝配置类
 * @author
 * @date
 */
@Data
@Entity
@Table(name = "tool_alipay_config")
public class AlipayConfig implements Serializable {

    @Id
    @Column(name = "config_id")
    private Long id;

    @NotBlank
    private String appId;

    @NotBlank
    private String privateKey;

    @NotBlank
    private String publicKey;

    private String signType="RSA2";

    @Column(name = "gateway_url")
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    private String charset= "utf-8";

    @NotBlank
    private String notifyUrl;

    @NotBlank
    private String returnUrl;

    private String format="JSON";

    @NotBlank
    private String sysServiceProviderId;

}
