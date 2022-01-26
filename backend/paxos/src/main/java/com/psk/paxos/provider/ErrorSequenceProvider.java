package com.psk.paxos.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class ErrorSequenceProvider {
    @Autowired
    public ErrorSequenceProvider() {
        this.errorSeq = new LinkedList<>();
    }

    private List<Integer> errorSeq;

    public List<Integer> getErrorSeq() {
        return errorSeq;
    }


    public void removeErrors() {
        this.errorSeq.clear();
    }
}
