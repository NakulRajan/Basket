package com.shopping.basket;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;


/** An endpoint class we are exposing for user related stuff */
@Api(name = "usersApi", version = "v1")
public class UsersAPI {
        /** A simple endpoint method that takes a email and says Hi back */
        @ApiMethod(name = "userDetails")
        public UserModel userDetails(@Named("email") String email) {
            UserModel response = new UserModel();
            response.setName("Nakul");
            response.setEmail(email);
            return response;
        }

}
