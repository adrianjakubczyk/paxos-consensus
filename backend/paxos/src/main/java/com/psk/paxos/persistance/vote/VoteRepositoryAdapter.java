package com.psk.paxos.persistance.vote;

import com.psk.paxos.domain.acceptor.port.AcceptorGatePort;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import com.psk.paxos.provider.AcceptorIdsProvider;
import com.psk.paxos.provider.SequenceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepositoryPort {
    private final SequenceProvider seqProvider;
    private final AcceptorGatePort acceptorGatePort;

    @Override
    public void createNewVoteSession(String voteName, Integer clientId) {
        final int presentSeq = seqProvider.getSeq();
        seqProvider.seqNextValue();

        AcceptorIdsProvider.findAcceptorIds()
                .forEach(acceptorId -> {
                    sendProposeOrAccepted(acceptorId, voteName, clientId, presentSeq);
                });

    }

    private void sendProposeOrAccepted(Integer acceptorId, String voteName, Integer clientId, Integer presentSeq) {
        acceptorGatePort.sendPropose(acceptorId, voteName, presentSeq);
        acceptorGatePort.sendAcceptedPropose(acceptorId, clientId, voteName);
    }
}
