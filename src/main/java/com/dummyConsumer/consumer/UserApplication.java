package com.dummyConsumer.consumer;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("v1") // Good practice: add application version here
public class UserApplication  extends Application{
}
