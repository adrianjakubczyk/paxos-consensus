package pl.kielce.tu.sonb.sonb.service.acceptor;

import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.acceptor.AcceptorDTO;
import pl.kielce.tu.sonb.sonb.domain.acceptor.VotingSessionDTO;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GlobalStateAdapter {

    private List<AcceptorDTO> acceptorDTOS = Stream.of(new AcceptorDTO(),new AcceptorDTO(),new AcceptorDTO()).collect(Collectors.toList());

    public VotingSessionDTO getCurrentVotingSession(int acceptorId) {
        List<VotingSessionDTO> votingSessionDTOS = acceptorDTOS.get(acceptorId).getVotingSessionDTOS();
        return votingSessionDTOS.isEmpty() ? null : votingSessionDTOS.get(votingSessionDTOS.size() - 1);
    }

    public AcceptorDTO getAcceptor(int acceptorId) {
        return acceptorDTOS.get(acceptorId);
    }
}