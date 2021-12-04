package pl.kielce.tu.sonb.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VotingSession {
    private String currentProblem;
    private final List<String> votes = new ArrayList<>();
}
