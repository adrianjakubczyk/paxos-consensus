package com.psk.paxos.domain.vote;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Data
@NoArgsConstructor
public class Vote {
    String presentVote;
    List<String> presentVotes = new ArrayList<>();
    String voteResult;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return presentVote.equals(vote.presentVote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(presentVote);
    }
}
