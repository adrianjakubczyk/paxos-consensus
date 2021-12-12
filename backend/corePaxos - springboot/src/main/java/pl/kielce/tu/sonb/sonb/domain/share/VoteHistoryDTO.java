package pl.kielce.tu.sonb.sonb.domain.share;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteHistoryDTO {
  private String currentProblem;
  private String finalVoteOfAcceptors;
  private List<String> currentProblemVotes = new ArrayList<>();
}
