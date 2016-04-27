package edu.rutgers.database.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import edu.rutgers.database.model.Business;
import edu.rutgers.database.util.DataIntegrator;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping(value="data/")
public class DataController {
	
	private static final Logger logger = LoggerFactory.getLogger(DataController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "search")
	public String home(HttpServletRequest request, String city, String state, String query) {
		ModelAndView modelAndView = new ModelAndView();
		List<Business> result = DataIntegrator.locationAndQuerySearch(city, state, query);
		System.out.println(result);
		modelAndView.addObject("json", result);
		
		return "search_form";
	}
	@RequestMapping(value = "form")
	public ModelAndView form() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("search_form");
		
		return modelAndView;
	}
	
}
