package com.psk.paxos.domain.vote;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateVoteCommand {
    private int acceptorId;
    private String voteName;
    private int clientId;
    private int presentSeq;
};
