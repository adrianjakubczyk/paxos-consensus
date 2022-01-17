package com.psk.paxos.utils;

import com.psk.paxos.domain.acceptor.Acceptor;
import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.vote.VoteSession;

import java.util.Objects;

public class AcceptorResponseFactory {
    public static AcceptorResponse buildResponse(Acceptor acceptor, VoteSession presentVoteSession, Integer prevSeq){
        if (Objects.isNull(presentVoteSession)) {
            //todo: add error ?
         return AcceptorResponse.builder()
                    .requestAccepted(true)
                    .currentSequenceNumber(prevSeq)
                    .build();

        }
        return AcceptorResponse.builder()
                .requestAccepted(true)
                .currentProblem(presentVoteSession.getPresentProblem())
                .currentProblemVotes(presentVoteSession.getVotes())
                .currentSequenceNumber(acceptor.getCurrentSequenceNumber())
                .build();
    }
}
