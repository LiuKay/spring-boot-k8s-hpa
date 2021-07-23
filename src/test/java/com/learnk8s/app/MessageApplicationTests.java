package com.learnk8s.app;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageApplicationTests {

	@Autowired
	MessageService messageService;

	@Test
	public void canaryTest() {
		Assertions.assertThat(messageService).isNotNull();
	}
}
