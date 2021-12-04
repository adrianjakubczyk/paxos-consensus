package pl.kielce.tu.sonb.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionDTO {
    private String currentProblem;
    private final List<String> votes = new ArrayList<>();
}
