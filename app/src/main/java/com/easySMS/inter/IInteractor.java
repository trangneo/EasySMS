package com.easySMS.inter;

import java.util.Map;

/**
 * Created by poiuyt on 9/16/16.
 */

public interface IInteractor  {
    void receiveRegisterRequest(String username, String email, String password );
    Map<String, Object> createUser(String username);
}
