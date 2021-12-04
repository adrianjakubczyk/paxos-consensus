package pl.kielce.tu.sonb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.sonb.domain.VoteHistoryDTO;
import pl.kielce.tu.sonb.service.ResultVotingAdapter;

@RestController
@RequestMapping("/acceptor")
public class VoteHistoryController {

    private final ResultVotingAdapter resultVotingAdapter = new ResultVotingAdapter();

    @GetMapping("/vote/history")
    public VoteHistoryDTO getVoteHistory() {
        return resultVotingAdapter.getVoteResult();
    }
}