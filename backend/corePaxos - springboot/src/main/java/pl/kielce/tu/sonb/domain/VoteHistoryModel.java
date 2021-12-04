package pl.kielce.tu.sonb.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class VoteHistoryModel {
  private String currentProblem;
  private String finalVoteOfAcceptors;
  private List<String> currentProblemVotes = new ArrayList<>();
}
