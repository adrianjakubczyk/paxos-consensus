package pl.kielce.tu.sonb.sonb.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import pl.kielce.tu.sonb.sonb.domain.ClientModel;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorDAOResponse;

import java.util.ArrayList;
import java.util.List;

@Getter
@Service
@Setter
public class ProposerCache {

    private List<AcceptorDAOResponse> acceptorsInfo = new ArrayList<>();

    private List<ClientModel> clients = new ArrayList<>();


    public ClientModel getCurrentClient(int clientId) {
        return clients.get(clientId);
    }

    public int getAcceptorsNumber() {
        return acceptorsInfo.size();
    }
}