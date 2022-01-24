package com.psk.paxos.persistance.acceptor;

import com.psk.paxos.domain.acceptor.Acceptor;
import com.psk.paxos.domain.acceptor.port.AcceptorFlowPort;
import com.psk.paxos.domain.acceptor.port.AcceptorRepositoryPort;
import com.psk.paxos.domain.vote.VoteSession;
import com.psk.paxos.provider.AcceptorSequenceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AcceptorFlowAdapter implements AcceptorFlowPort {
    private final AcceptorRepositoryPort acceptorRepositoryPort;
    private final AcceptorSequenceProvider acceptorSequenceProvider;

    @Override
    public boolean isSequenceCorrect(Integer acceptorId, String voteName, Integer seq) {
        acceptorSequenceProvider.setPreviousSeq(acceptorRepositoryPort.findById(acceptorId).getCurrentSequenceNumber());
        return acceptorRepositoryPort.findById(acceptorId).getCurrentSequenceNumber().equals(seq);
    }

    @Override
    public void updateVoteName(Integer acceptorId, String voteName) {
        acceptorRepositoryPort.findById(acceptorId).setVoteName(voteName);
    }

    @Override
    public void acceptNewVoteSession(Integer acceptorId, Integer newSeq, String acceptedValue) {
        Acceptor acceptor = acceptorRepositoryPort.findById(acceptorId);
        List<VoteSession> currentVoteSession = acceptor.getVotingSessions();
        currentVoteSession.add(VoteSession.builder().presentVote(acceptedValue).votes(Collections.emptyList()).build());
        acceptor.setVotingSessions(currentVoteSession);
        acceptor.setCurrentSequenceNumber(newSeq);
    }

    @Override
    public void acceptNewVote(int acceptorId, int sequenceNumber, String voteName) {
        Acceptor acceptor = acceptorRepositoryPort.findById(acceptorId);
        Collection<String> votes = new LinkedList<>(acceptor.getCurrentVotingSession().getVotes());
        votes.add(voteName);

        if (isCurrentSequence(sequenceNumber, acceptor)) {
            acceptor.getCurrentVotingSession().setVotes(votes);
            acceptor.setCurrentSequenceNumber(sequenceNumber);
        }
    }

    private boolean isCurrentSequence(int currentSeq, Acceptor acceptor) {
        return acceptor.getCurrentSequenceNumber().equals(currentSeq);
    }
}
