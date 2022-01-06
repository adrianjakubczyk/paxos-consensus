package pl.kielce.tu.sonb.sonb.service;

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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import pl.kielce.tu.sonb.sonb.domain.AcceptedCommand;
import pl.kielce.tu.sonb.sonb.domain.share.AcceptorDAOResponse;
import pl.kielce.tu.sonb.sonb.domain.share.ProposeCommand;
import pl.kielce.tu.sonb.sonb.domain.share.VoteHistoryDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AcceptorDAO {

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
  ;
  private final HttpHeaders headers = new HttpHeaders();
  private List<VoteHistoryDTO> listOfVoteHistoryModels = new ArrayList<>();

  public List<VoteHistoryDTO> readHistory() {
    return listOfVoteHistoryModels;
  }

  public void readSingleHistory() {
    String effectiveUrl = processToEffectiveUrl(AcceptorMicroserviceAPI.VOTE_HISTORY.getProposeUrl(), null);
    VoteHistoryDTO voteHistoryModel = restTemplate.getForObject(effectiveUrl, VoteHistoryDTO.class);
    if (!Objects.isNull(voteHistoryModel)) {
      listOfVoteHistoryModels.add(voteHistoryModel);
    }
  }

  @Getter
  @AllArgsConstructor
  public enum AcceptorMicroserviceAPI { //INFO: All urls given here, hits on endpoints in Acceptor application, class: AcceptorAPI
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

  public boolean sendProposeAndAwaitResponse(String effectiveUrl, ProposeCommand proposeRequestModel) {
    try {

      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(proposeRequestModel), headers);
      String resultAsJsonStr = restTemplate.postForObject(effectiveUrl, request, String.class);
      AcceptorDAOResponse acceptorDAOResponseModel = objectMapper.readValue(resultAsJsonStr, AcceptorDAOResponse.class);
      return acceptorDAOResponseModel.getRequestAccepted();
    } catch (JsonProcessingException ignored) {

    }
    return false;
  }

  public void sendAccepted(String effectiveUrl, AcceptedCommand acceptedCommand) {
    try {
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(acceptedCommand), headers);
      restTemplate.postForObject(effectiveUrl, request, String.class);
    } catch (HttpClientErrorException | JsonProcessingException ignored) {
    }
  }


  public void sendEnableErrorRequest(Integer acceptorId, Integer errorType) {
    String effectiveUrl = processToEffectiveUrl(AcceptorMicroserviceAPI.ENABLE_ERROR.getProposeUrl(), acceptorId);

    try {
      ProposeCommand proposeRequestModel = new ProposeCommand(String.valueOf(errorType), null);
      headers.setContentType(MediaType.APPLICATION_JSON);
      HttpEntity<String> request = new HttpEntity<>(objectMapper.writeValueAsString(proposeRequestModel), headers);
      restTemplate.postForObject(effectiveUrl, request, String.class);
    } catch (HttpClientErrorException | JsonProcessingException ignored) {
    }
  }

  public void sendDisableErrorRequest(Integer acceptorId) {
    String effectiveUrl = processToEffectiveUrl(AcceptorMicroserviceAPI.DISABLE_ERROR.getProposeUrl(), acceptorId);
    try {
      restTemplate.postForObject(effectiveUrl, null, String.class);
    } catch (HttpClientErrorException ignored) {

    }
  }

  public AcceptorDAOResponse readStateOfAcceptor(int acceptorId) {
    String effectiveUrl = processToEffectiveUrl(AcceptorMicroserviceAPI.FETCH_ACCEPTOR_STATE.getProposeUrl(), acceptorId);
    try {
      return restTemplate.getForObject(effectiveUrl, AcceptorDAOResponse.class);
    } catch (ResourceAccessException ignored) {
    }
    return null;
  }

  public String processToEffectiveUrl(String urlWithParam, Integer acceptorId) {
    return urlWithParam.replace(":acceptorId", String.valueOf(acceptorId));
  }
}
