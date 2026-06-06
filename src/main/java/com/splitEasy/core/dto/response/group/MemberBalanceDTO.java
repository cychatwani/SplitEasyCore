package com.splitEasy.core.dto.response.group;

import com.splitEasy.core.dto.balance.BalanceDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberBalanceDTO {
    private String publicId;
    private List<BalanceDTO> balances;   // net per currency, e.g. [{INR, +1000}, {USD, -299}]
}