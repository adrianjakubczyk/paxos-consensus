package com.psk.paxos.cron;

import com.psk.paxos.domain.acceptor.AcceptorResponse;
import com.psk.paxos.domain.acceptor.port.AcceptorCommunicationPort;
import com.psk.paxos.domain.vote.Vote;
import com.psk.paxos.domain.vote.port.CronActionVoteHistoryPort;
import com.psk.paxos.domain.vote.port.VoteRepositoryPort;
import com.psk.paxos.provider.AcceptorIdsProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CronActionService implements CronActionVoteHistoryPort {
    private final AcceptorCommunicationPort acceptorCommunicationPort;
    private final VoteRepositoryPort voteRepositoryPort;

    @Override
    @Scheduled(fixedRate = 5000)
    public void generateVote() {
        List<String> presentVotes = voteRepositoryPort.findAll().stream().map(Vote::getPresentVote).collect(Collectors.toList());
        List<String> votes = new ArrayList<>();
        Vote historyVote = new Vote();
        AcceptorIdsProvider.findAcceptorIds().forEach((acceptorId) -> {
            AcceptorResponse acceptorResponseModel = acceptorCommunicationPort.findAcceptorResponseById(acceptorId);
            if (Objects.nonNull(acceptorResponseModel.getPresentVotes())) {
                votes.addAll(acceptorResponseModel.getPresentVotes());
            }
            if (Objects.nonNull(acceptorResponseModel.getPresentVote())) {
                historyVote.setPresentVote(acceptorResponseModel.getPresentVote());
            }
        });

        historyVote.setPresentVotes(votes);
        historyVote.setVoteResult(calculateVoteResult(votes));

        if (!presentVotes.contains(historyVote.getPresentVote()))
            voteRepositoryPort.createNewVoteHistory(historyVote);
    }

    private String calculateVoteResult(List<String> votes) {
        long voteYes = votes.stream().filter("Accepted"::equals).count();
        long voteNo = votes.stream().filter("Rejected"::equals).count();

        if (voteYes > voteNo) {
            return "Accepted";
        } else if (voteNo > voteYes) {
            return "Rejected";
        } else {
            return "notresolved";
        }
    }
}
