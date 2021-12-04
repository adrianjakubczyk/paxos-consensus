package pl.kielce.tu.sonb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoteHistoryModel {

    private String currentProblem;
    private List<String> currentProblemVotes;
    private String finalVoteOfAcceptors;
}
