package com.psk.paxos.utils;

import com.psk.paxos.domain.acceptor.Acceptor;
import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.vote.VoteSession;

import java.util.Arrays;
import java.util.Objects;

public class AcceptorResponseFactory {
    public static AcceptorResponse buildResponse(Acceptor acceptor, VoteSession presentVoteSession, Integer prevSeq, Integer firstError) {


        if (Arrays.asList(1, 2).contains(acceptor.getCurrentError())) {
            switch (acceptor.getCurrentError()) {
                case 1:
                    return AcceptorResponse.builder()
                            .isAcceptedProposeVote(Boolean.TRUE)
                            .currentError(acceptor.getCurrentError())
                            .currentSequenceNumber(firstError)
                            .build();
                case 2:
                    return AcceptorResponse.builder()
                            .isAcceptedProposeVote(Boolean.TRUE)
                            .currentError(acceptor.getCurrentError())
                            .currentSequenceNumber(-1000)
                            .build();
            }
        }


        if (Objects.isNull(presentVoteSession)) {
            return AcceptorResponse.builder()
                    .isAcceptedProposeVote(true)
                    .currentSequenceNumber(prevSeq)
                    .build();

        }
        return AcceptorResponse.builder()
                .isAcceptedProposeVote(true)
                .presentVote(presentVoteSession.getPresentVote())
                .presentVotes(presentVoteSession.getVotes())
                .currentSequenceNumber(acceptor.getCurrentSequenceNumber())
                .build();
    }
}
