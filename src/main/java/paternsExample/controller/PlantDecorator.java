package paternsExample.controller;

import java.util.Map;

public interface PlantDecorator {

	String getLabel();
	String getTemplate();
	void processSubmission(Map<String,String> params, Plant plant);
	
}
