package pl.kielce.tu.sonb.sonb.domain.acceptor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AcceptorDTO {

    private Integer currentError=0;
    private Integer currentSequenceNumber = 1;
    private List<VotingSessionDTO> votingSessionDTOS = new ArrayList<>();
    private String stagingArea="";

    public VotingSessionDTO getCurrentVotingSession() {
        return votingSessionDTOS.isEmpty() ? null : votingSessionDTOS.get(votingSessionDTOS.size() - 1);
    }

    public AcceptorDTO() {
        super();
    }
}