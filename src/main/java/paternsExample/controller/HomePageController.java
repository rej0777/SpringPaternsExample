package paternsExample.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import paternsExample.helper.PlantHelper;

@Controller
//@RestController
@ImportResource({"/WEB-INF/classes/applicationContext.xml"})
public class HomePageController {

	@Autowired
	private PlantAttributeGroups plantAttributeGroups;
	
	private List<PlantDecorator> plantDecorators;
	
//	Map<String, PlantDecorator> allPlantDecorators;
	private List<Plant> allPlants;
	
	public HomePageController() {
		plantDecorators = new ArrayList<PlantDecorator>();
		allPlants = new ArrayList<Plant>();
		/* przeniesione do xml
		
		allPlantDecorators = new HashMap<String, PlantDecorator>();
		
		Woody woody = new Woody();
		Herbaceous herbaceous = new Herbaceous(); 
		Native nativegeo = new Native();
		
		allPlantDecorators.put(woody.getLabel(), woody);
		allPlantDecorators.put(herbaceous.getLabel(), herbaceous);
		allPlantDecorators.put(nativegeo.getLabel(), nativegeo);
		*/
	}

	//http://localhost:8080/home?name=test
	@GetMapping("/home")
	public String home(@RequestParam(value = "name", required = false, defaultValue = "Frend") String name, Model model) {		
		model.addAttribute("name", name);		
		return "home";
	}
	
	@RequestMapping("/selectattributes")
	public String selectAttributes(Model model) {
		plantDecorators = new ArrayList<PlantDecorator>();
	model.addAttribute("components", plantAttributeGroups.getDecorators().keySet());		//allPlantDecorators.keySet());		
		return "selectattributes";
	}
	
	@RequestMapping("/addplant")
	public String sddPlant(@RequestParam Map<String,String>requestParams, Model model) {	
		
		Set<String> selectedKeys = requestParams.keySet();
		List<String> templates = new ArrayList<String>();
		for(String key : selectedKeys) {
			PlantDecorator plantDecorator2 = plantAttributeGroups.getDecorators().get(key); //allPlantDecorators.get(key);
			//colection with olny selected decorators
			
			if (plantDecorator2 !=null) {
				plantDecorators.add(plantDecorator2);
				templates.add( plantDecorator2.getTemplate());
			}
		}
		
	//	allPlantDecorators.get(selectedKeys);		
		model.addAttribute("components", templates);
		model.addAttribute("plant", new Plant());
		return "addplant";
	}
	

	
	@RequestMapping("/saveplant")
	public String savePlant(Plant plant, @RequestParam Map<String,String> requestParams) {
		// implement the command pattern to process our decorators.
		// iterate over our Decorators, and invoke the command method
		for (PlantDecorator plantDecorator : plantDecorators) {
			plantDecorator.processSubmission(requestParams, plant);//
		}
		// add the plant submitted to our collection of saved plants.
		allPlants.add(plant);
		return "saveplant";
	}
	

	@RequestMapping(value="/generateJSON", method=RequestMethod.GET, produces="application/json")
	public @ResponseBody String generateJSON() {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/WEB-INF/classes/applicationContext.xml");
			
		// StringBuilder to contain our JSON.
		StringBuilder json = new StringBuilder();
		
		// iterate over our collection of plants, and invoke the visitor.
		for (Plant plant : allPlants) {
			Map<String, String> additionalProperties = plant.getAdditionalProperties();
			String helperBean = additionalProperties.get(Plant.HELPER);
			PlantHelper plantHelper = context.getBean(helperBean, PlantHelper.class);
			String plantJSON = plant.accept(plantHelper);
			json.append(plantJSON);
		}
		
		return json.toString();
	}
}
