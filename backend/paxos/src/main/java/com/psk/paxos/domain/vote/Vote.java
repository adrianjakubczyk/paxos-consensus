package com.psk.paxos.domain.vote;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
public class Vote {
    String presentVote;
    List<String> presentVotes = new ArrayList<>();
    String voteResult;
}
