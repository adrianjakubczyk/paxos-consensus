package pl.kielce.tu.sonb.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.sonb.domain.VoteHistoryModel;
import pl.kielce.tu.sonb.service.VoteResultService;

@RestController
@RequestMapping("/acceptor")
public class HistoryApi {

    private final VoteResultService historyService = new VoteResultService();

    @GetMapping("/vote/history")
    public VoteHistoryModel getHistory() {
        return historyService.getVoteResult();
    }
}