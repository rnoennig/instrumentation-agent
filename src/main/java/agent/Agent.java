package agent;

import java.lang.instrument.Instrumentation;

import net.bytebuddy.agent.builder.AgentBuilder;
import net.bytebuddy.matcher.ElementMatchers;

public class Agent {

	public static void premain(String args, Instrumentation instrumentation)
			throws NoSuchFieldException, SecurityException, Exception {
		System.out.println("---- PREMAIN ----");

		AgentBuilder agentBuilder = new AgentBuilder.Default().with(AgentBuilder.Listener.StreamWriting.toSystemError().withTransformationsOnly());
		agentBuilder = methodShouldReturnBooleanValue("thirdpartylibrary.Server", "isExecEnvironmentTrue", true, agentBuilder);
		agentBuilder = methodShouldLogParameters("thirdpartylibrary.Server", "isExecEnvironmentTrue", agentBuilder);
		agentBuilder.installOn(instrumentation);
	}

	private static AgentBuilder methodShouldLogParameters(String targetClassName, String targetMethodName, AgentBuilder agentBuilder) {
		Class<?> adviceClass = LogMethodAdvice.class;
		return methodShouldBeAdviced(targetClassName, targetMethodName, adviceClass, agentBuilder);
	}

	private static AgentBuilder methodShouldReturnBooleanValue(String targetClassName, String targetMethodName, Boolean value, AgentBuilder agentBuilder) {
		Class<?> returnTrueAdviceClass = ReturnTrueAdvice.class;
		Class<?> returnFalseAdviceClass = ReturnFalseAdvice.class;
		Class<?> adviceClass = value ? returnTrueAdviceClass : returnFalseAdviceClass;
		return methodShouldBeAdviced(targetClassName, targetMethodName, adviceClass, agentBuilder);
	}
	
	private static AgentBuilder methodShouldBeAdviced(String targetClassName, String targetMethodName, Class<?> adviceClass, AgentBuilder agentBuilder) {
		return agentBuilder
				.type(ElementMatchers.named(targetClassName))
				.transform(new AgentBuilder.Transformer.ForAdvice().advice(ElementMatchers.named(targetMethodName),
						adviceClass.getName()));
	}

}