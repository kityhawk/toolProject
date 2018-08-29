package com.kityhawk.test.hello;

import org.springframework.stereotype.Component;

@Component
public class MessageServiceImp implements MessageService {

	public String getMessage() {
		// TODO Auto-generated method stub
		 return "Hello World!";
	}

}
