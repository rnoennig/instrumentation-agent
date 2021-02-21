package agent;

import java.util.Arrays;
import java.util.stream.Collectors;

import net.bytebuddy.asm.Advice;

public class LogMethodAdvice {
	@Advice.OnMethodEnter
	public static String onEnter(@Advice.This Object self, @Advice.AllArguments Object... args) {
		String argsDelimitedValues = Arrays.stream(args).map(Object::toString).collect(Collectors.joining(","));
		String methodName = new Throwable().getStackTrace()[0].getMethodName();
		String debugText = "|-> " + methodName + " with arguments: " + argsDelimitedValues;
		System.out.println(debugText);
		return methodName;
	}

	@Advice.OnMethodExit
	public static void onExit(@Advice.Enter String methodName) throws Throwable {
		System.out.println("->| " + methodName);
	}
}
