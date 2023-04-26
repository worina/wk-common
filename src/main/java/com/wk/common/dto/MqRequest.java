package com.wk.common.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MqRequest<T> implements Serializable {
    private String topic;
    private T data;
    private String messageKey;

}
