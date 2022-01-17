package com.psk.paxos.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceProvider {
  @Autowired
  public SequenceProvider() {
    this.seq = 0;
  }

  private Integer seq;

  public Integer getSeq() {
    return seq;
  }

  public void seqNextValue() {
    this.seq = this.seq + 1;
  }
}
