package externalClass;

import java.util.Map;

import paternsExample.controller.Plant;
import paternsExample.controller.PlantDecorator;

public class Evergreen implements PlantDecorator {

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "Evergreen";
	}

	@Override
	public String getTemplate() {
		// TODO Auto-generated method stub
		return "fragments/evergreen";
	}

	@Override
	public void processSubmission(Map<String, String> params, Plant plant) {
		String string = params.get("broadleafconifer") ;
		
		System.out.print(string);	
		
	}

}
