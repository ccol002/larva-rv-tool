package transactionsystem;

import java.io.IOException;

public class Main {

	public static void main(String [] args) 
	{
		if(args.length > 0){
			int scenario = Integer.parseInt(args[0]);
			
			switch(scenario){
				case 1: Scenarios.runViolatingScenarioForProperty(6); break;
				case 2: Scenarios.runNonViolatingScenarioForProperty(8); break;
				case 3: Scenarios.runViolatingScenarioForProperty(10); break;
			}
		}
		//Scenarios.runAllScenarios();
	}
	
}
