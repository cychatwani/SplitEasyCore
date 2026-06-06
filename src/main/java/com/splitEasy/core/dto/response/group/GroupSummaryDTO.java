package com.splitEasy.core.dto.response.group;

import com.splitEasy.core.dto.balance.BalanceDTO;
import com.splitEasy.core.entity.group.Group;
import com.splitEasy.core.enums.GroupType;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@Builder
public class GroupSummaryDTO {
    private String id;
    private String name;
    private GroupType type;
    private String defaultCurrencyCode;
    private Integer memberCount;
    private List<BalanceDTO> myBalance;   // placeholder — empty until BalanceLedger
    private Instant createdAt;

    public static GroupSummaryDTO from(Group g) {
        return GroupSummaryDTO.builder()
                .id(g.getId())
                .name(g.getName())
                .type(g.getType())
                .defaultCurrencyCode(g.getDefaultCurrency().getCode())  // .getCode() = the @Id, no lazy load
                .memberCount(g.getMemberCount())
                .myBalance(List.of())
                .createdAt(g.getCreatedAt())
                .build();
    }
}