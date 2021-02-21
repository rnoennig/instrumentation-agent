package agent;

import net.bytebuddy.asm.Advice;

public class ReturnFalseAdvice {

	@Advice.OnMethodExit
	public static void onExit(@Advice.Return(readOnly = false) Boolean returnValue) throws Throwable {
		returnValue = false;
	}
}
