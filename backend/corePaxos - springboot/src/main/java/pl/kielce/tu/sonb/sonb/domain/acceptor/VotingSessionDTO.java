package pl.kielce.tu.sonb.sonb.domain.acceptor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VotingSessionDTO {
    private String currentProblem;
    private final List<String> votes = new ArrayList<>();
}
