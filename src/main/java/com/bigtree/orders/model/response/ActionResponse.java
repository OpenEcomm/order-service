package com.bigtree.orders.model.response;

import com.bigtree.orders.model.enums.Action;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActionResponse {
    
    private String object;
    private String uniqueId;
    private Integer id;
    private Action action;
    private String requestDescription;
    private String responseDescription;
    private String error;
    private boolean status;
}
