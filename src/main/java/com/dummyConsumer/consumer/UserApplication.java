package com.dummyConsumer.consumer;

import com.kumuluz.ee.discovery.annotations.RegisterService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@RegisterService
@ApplicationPath("v1") // Good practice: add application version here
public class UserApplication  extends Application{
}
