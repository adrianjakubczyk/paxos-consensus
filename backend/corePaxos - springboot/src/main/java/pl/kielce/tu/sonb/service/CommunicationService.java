package pl.kielce.tu.sonb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.kielce.tu.sonb.domain.AcceptedRequestModel;
import pl.kielce.tu.sonb.domain.AcceptorResponseModel;
import pl.kielce.tu.sonb.domain.ProposeRequestModel;
import pl.kielce.tu.sonb.domain.VoteHistoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class CommunicationService {

  @Bean
  public RestTemplate restTemplate() {
    final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    requestFactory.setConnectTimeout(2000);
    requestFactory.setReadTimeout(2000);
    return new RestTemplate(requestFactory);
  }

  @Autowired
  RestTemplate restTemplate = new RestTemplate();

  private List<VoteHistoryModel> listOfVoteHistoryModels = new ArrayList<>();

  public List<VoteHistoryModel> readHistory() {
    return listOfVoteHistoryModels;
  }

  public void readSingleHistory() {
    String effectiveUrl = processToEffectiveUrl(AcceptorApi.VOTE_HISTORY.getProposeUrl(), null);
    VoteHistoryModel voteHistoryModel = restTemplate.getForObject(effectiveUrl, VoteHistoryModel.class);
    if (!Objects.isNull(voteHistoryModel)) {
      listOfVoteHistoryModels.add(voteHistoryModel);
    }
  }

  @Getter
  @AllArgsConstructor
  public enum AcceptorApi {
    ADD_NEW_PROBLEM("http://localhost:2120/acceptor/:acceptorId/add-new-problem", "http://localhost:2120/acceptor/:acceptorId/accepted-new-problem"),
    ADD_NEW_VOTE("http://localhost:2120/acceptor/:acceptorId/add-new-vote",
        "http://localhost:2120/acceptor/:acceptorId/accepted-new-vote"),
    ENABLE_ERROR("http://localhost:2120/acceptor/:acceptorId/enable-error", null),
    DISABLE_ERROR("http://localhost:2120/acceptor/:acceptorId/disable-error", null),
    FETCH_ACCEPTOR_STATE("http://localhost:2120/acceptor/:acceptorId/fetch-acceptor-state", null),
    VOTE_HISTORY("http://localhost:2120/acceptor/vote/history", null);

    private String proposeUrl;

    private String acceptedUrl;
  }

  public String processToEffectiveUrl(String urlWithParam, Integer acceptorId) {
    return urlWithParam.replace(":acceptorId", String.valueOf(acceptorId));
  }
}
