package pl.kielce.tu.sonb.sonb.domain;

import lombok.Data;

@Data
public class ConfirmCommand {
  private int newSequenceId;
  private String acceptedValue;
}
