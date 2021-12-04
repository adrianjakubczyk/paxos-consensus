package pl.kielce.tu.sonb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import pl.kielce.tu.sonb.domain.AcceptorResponse;
import pl.kielce.tu.sonb.domain.VoteHistoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResultVotingAdapter {

    @Bean
    public RestTemplate restTemplate() {
        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(2000);
        requestFactory.setReadTimeout(2000);
        return new RestTemplate(requestFactory);
    }

    @Autowired
    RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();



    public VoteHistoryDTO getVoteResult() {
        List<String> votes = new ArrayList<>();
        VoteHistoryDTO voteHistoryDTO = new VoteHistoryDTO();
        try {
            for (int i = 0; i < 3; i++) {
                final String uri = String.format("http://localhost:8090/acceptor/%d/fetch-acceptor-state", i);
                String statesAsJson = restTemplate.getForObject(uri, String.class);
                AcceptorResponse acceptorResponse = objectMapper.readValue(statesAsJson, AcceptorResponse.class);
                if (!Objects.isNull(acceptorResponse.getCurrentProblemVotes())) {
                    votes.addAll(acceptorResponse.getCurrentProblemVotes());
                }
                if (!Objects.isNull(acceptorResponse.getCurrentProblem())) {
                    voteHistoryDTO.setCurrentProblem(acceptorResponse.getCurrentProblem());
                } else {
                    voteHistoryDTO.setCurrentProblem("");
                }
            }
            voteHistoryDTO.setCurrentProblemVotes(votes);
            voteHistoryDTO.setFinalVoteOfAcceptors(compareQuorumOfVotes(votes));
            return voteHistoryDTO;
        } catch (JsonProcessingException ignored) {
        }
        return null;
    }

    private String compareQuorumOfVotes(List<String> votes) {
        long voteYes = votes.stream().filter("Green"::equals).count();
        long voteNo = votes.stream().filter("Red"::equals).count();

        if (voteYes > voteNo) {
            return "Green";
        } else if (voteNo > voteYes) {
            return "Red";
        } else {
            return "Nie można określić.";
        }
    }
}