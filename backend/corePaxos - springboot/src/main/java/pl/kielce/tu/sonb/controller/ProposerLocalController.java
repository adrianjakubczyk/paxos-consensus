package pl.kielce.tu.sonb.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kielce.tu.sonb.domain.VoteHistoryModel;
import pl.kielce.tu.sonb.service.CommunicationService;

import java.util.List;

@RestController
@RequestMapping("/proposer")
@RequiredArgsConstructor
@AllArgsConstructor
public class ProposerLocalController {
  private final CommunicationService communicationService;

  @GetMapping("/vote-history")
  public List<VoteHistoryModel> getVoteHistory() {
    return communicationService.readHistory();
  }

  @GetMapping("/get-single-history")
  public void getSingleHistory() {
    communicationService.readSingleHistory();
  }
}