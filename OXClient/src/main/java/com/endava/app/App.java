package com.endava.app;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.endava.controller.GameControllerImpl;
import com.endava.gui.GameFrame;
import com.endava.gui.GameFrameImpl;
import com.endava.model.GameHandle;
import com.endava.model.GameHandleImpl;


public class App {
	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/application-Context.xml");
	}
}
