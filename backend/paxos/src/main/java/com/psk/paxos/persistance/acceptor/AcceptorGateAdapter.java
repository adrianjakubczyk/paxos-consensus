package com.psk.paxos.persistance.acceptor;

import com.psk.paxos.VoteType;
import com.psk.paxos.domain.acceptor.port.AcceptorCommunicationPort;
import com.psk.paxos.domain.acceptor.port.AcceptorGatePort;
import com.psk.paxos.domain.client.Client;
import com.psk.paxos.domain.client.port.ClientRepositoryPort;
import com.psk.paxos.provider.SequenceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AcceptorGateAdapter implements AcceptorGatePort {
    private final AcceptorCommunicationPort acceptorCommunicationPort;
    private final ClientRepositoryPort clientRepositoryPort;
    private final SequenceProvider sequenceProvider;

    @Override
    public void sendPropose(int acceptorId, String voteName, int currentSeq) {
        acceptorCommunicationPort.createNewVote(acceptorId, voteName, currentSeq);
    }

    @Override
    public void sendAcceptedPropose(int acceptorId, Integer clientId, String voteName, VoteType voteType) {
        Client presentClient = clientRepositoryPort.findById(clientId);
        presentClient.setSequenceNumber(sequenceProvider.getSeq());
        if (Objects.equals(voteType, VoteType.START))
            acceptorCommunicationPort.acceptedNewProposeVote(acceptorId, presentClient.getSequenceNumber(), voteName);
        else
            acceptorCommunicationPort.acceptedNewVote(acceptorId, presentClient.getSequenceNumber(), voteName);
    }
}
