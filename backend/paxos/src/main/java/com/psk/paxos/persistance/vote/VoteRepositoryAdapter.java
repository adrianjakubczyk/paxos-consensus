package com.psk.paxos.persistance.vote;

import com.psk.paxos.enums.VoteType;
import com.psk.paxos.domain.acceptor.port.AcceptorGatePort;
import com.psk.paxos.domain.client.port.ClientRepositoryPort;
import com.psk.paxos.domain.vote.CreateVoteCommand;
import com.psk.paxos.domain.vote.Vote;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import com.psk.paxos.provider.AcceptorIdsProvider;
import com.psk.paxos.provider.SequenceProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VoteRepositoryAdapter implements VoteRepositoryPort {
    private final SequenceProvider seqProvider;
    private final AcceptorGatePort acceptorGatePort;
    private final ClientRepositoryPort clientRepositoryPort;
    private final List<Vote> votesData = new LinkedList<>();

    @Override
    public void createNewVoteSession(String voteName, Integer clientId) {
        final int presentSeq = seqProvider.getSeq();
        seqProvider.seqNextValue();
        activateVoteNodes(voteName, clientId, presentSeq, VoteType.START);
    }

    @Override
    public void createNewVote(String voteName, Integer clientId) {
        int clientSeq = clientRepositoryPort.findById(clientId).getSequenceNumber();
        activateVoteNodes(voteName, clientId, clientSeq, VoteType.CURRENT);
    }

    @Override
    public void createNewVoteHistory(Vote vote) {
        votesData.add(vote);
    }

    @Override
    public Collection<Vote> findAll() {
        return votesData;
    }

    private void activateVoteNodes(String voteName, Integer clientId, int seq, VoteType voteType) {
        AcceptorIdsProvider.findAcceptorIds()
                .forEach(acceptorId -> {
                    CreateVoteCommand command = CreateVoteCommand.builder()
                            .acceptorId(acceptorId)
                            .clientId(clientId)
                            .presentSeq(seq)
                            .voteName(voteName)
                            .build();
                    sendProposeOrAccepted(command,voteType);
                });
    }

    private void sendProposeOrAccepted(CreateVoteCommand command,VoteType voteType) {
        acceptorGatePort.sendPropose(command.getAcceptorId(), command.getVoteName(), command.getPresentSeq());
        acceptorGatePort.sendAcceptedPropose(command.getAcceptorId(), command.getClientId(), command.getVoteName(),voteType);
    }
}
