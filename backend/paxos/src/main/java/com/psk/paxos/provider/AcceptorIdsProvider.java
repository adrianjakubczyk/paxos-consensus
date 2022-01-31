package com.psk.paxos.provider;

import java.util.Arrays;
import java.util.List;


public class AcceptorIdsProvider {
    private static final List<Integer> acceptorIds = Arrays.asList(0, 1, 2, 3, 4,5,6,7);
    public static final int ACCEPTORS_SIZE = acceptorIds.size();

    public static List<Integer> findAcceptorIds() {
        return acceptorIds;
    }
}
