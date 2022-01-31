package com.psk.paxos.persistance.acceptor;

import com.psk.paxos.domain.acceptor.Acceptor;
import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.acceptor.port.AcceptorCommunicationPort;
import com.psk.paxos.domain.acceptor.port.AcceptorFlowPort;
import com.psk.paxos.domain.acceptor.port.AcceptorRepositoryPort;
import com.psk.paxos.domain.vote.VoteSession;
import com.psk.paxos.provider.AcceptorIdsProvider;
import com.psk.paxos.provider.AcceptorSequenceProvider;
import com.psk.paxos.provider.ErrorSequenceProvider;
import com.psk.paxos.utils.AcceptorResponseFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AcceptorCommunicationAdapter implements AcceptorCommunicationPort {
    private final AcceptorFlowPort acceptorFlowPort;
    private final AcceptorRepositoryPort acceptorRepositoryPort;
    private final AcceptorSequenceProvider acceptorSequenceProvider;
    private final ErrorSequenceProvider errorSequenceProvider;

    @Override
    public AcceptorResponse createNewVote(Integer acceptorId, String voteName, Integer seq) {
        if (acceptorFlowPort.isSequenceCorrect(acceptorId, voteName, seq)) {
            acceptorFlowPort.updateVoteName(acceptorId, voteName);
            return acceptedResponse();
        }
        return rejectResponse();
    }

    @Override
    public void acceptedNewProposeVote(Integer acceptorId, Integer newSeq, String acceptedValue) {
        acceptorFlowPort.acceptNewVoteSession(acceptorId, newSeq, acceptedValue);
    }

    @Override
    public List<AcceptorResponse> findAcceptors() {
        return AcceptorIdsProvider.findAcceptorIds()
                .stream()
                .map(this::findAcceptorResponseById)
                .collect(Collectors.toList());
    }

    @Override
    public void acceptedNewVote(int acceptorId, int sequenceNumber, String voteName) {
        acceptorFlowPort.acceptNewVote(acceptorId, sequenceNumber, voteName);
    }

    @Override
    public void createErrorOnAcceptor(Integer acceptorId, Integer errorType) {
        Acceptor acceptor = acceptorRepositoryPort.findById(acceptorId);
        acceptor.setCurrentError(Integer.valueOf(errorType));
    }

    @Override
    public void removeErrorOnAcceptor(Integer acceptorId) {
        errorSequenceProvider.removeErrors();
        Acceptor acceptor = acceptorRepositoryPort.findById(acceptorId);
        acceptor.setCurrentError(0);
    }

    @Override
    public AcceptorResponse findAcceptorResponseById(Integer acceptorId) {
        Acceptor acceptor = acceptorRepositoryPort.findById(acceptorId);
        acceptorSequenceProvider.setPreviousSeq(acceptor.getCurrentSequenceNumber());
        if (acceptor.getCurrentError() == 1) {
            errorSequenceProvider.getErrorSeq().add(acceptor.getCurrentSequenceNumber());
            if (errorSequenceProvider.getErrorSeq().size() > 1) {
                errorSequenceProvider.getErrorSeq().remove(1);
            }
        }

        VoteSession presentVotingSession = acceptorRepositoryPort.findPresentVotingSession(acceptorId);
        Integer firstError = acceptor.getCurrentError() == 1  ? errorSequenceProvider.getErrorSeq().get(0) : null;
        return AcceptorResponseFactory.buildResponse(acceptor, presentVotingSession, acceptorSequenceProvider.getPreviousSeq(), firstError);
    }

    private AcceptorResponse acceptedResponse() {
        return AcceptorResponse.builder().isAcceptedProposeVote(true).build();
    }

    private AcceptorResponse rejectResponse() {
        return AcceptorResponse.builder().isAcceptedProposeVote(false).build();
    }
}
