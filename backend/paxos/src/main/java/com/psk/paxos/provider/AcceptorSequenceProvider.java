package com.psk.paxos.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AcceptorSequenceProvider {
    @Autowired
    public AcceptorSequenceProvider() {
        this.previousSeq = 0;
    }

    private Integer previousSeq;

    public Integer getPreviousSeq() {
        return previousSeq;
    }

    public void setPreviousSeq(Integer previousSeq) {
        this.previousSeq = previousSeq;
    }
}
