package com.oceanbank.webapp.redirector.controller;

import com.oceanbank.webapp.redirector.CheckImageUrl;
import com.oceanbank.webapp.redirector.ImageLocation;
import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping({ "/img" })
public class HomeController {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Value("${image.url}")
	private String imageUrl;

	@RequestMapping(method = { org.springframework.web.bind.annotation.RequestMethod.GET })
	public String uriTemplate(@RequestParam String acc,
			@RequestParam String ser, @RequestParam String amo,
			@RequestParam String dat, Model model) {
		model.addAttribute("dat", dat);
		model.addAttribute("acc", acc);

		String newSer = ser.replaceAll("^0*", "");
		model.addAttribute("ser", newSer);

		DecimalFormat df = new DecimalFormat("0.00");
		String newAmo = df.format(new Double(amo));
		model.addAttribute("amo", newAmo);

		this.LOGGER.info("dat is " + dat);
		this.LOGGER.info("acc is " + acc);
		this.LOGGER.info("ser is " + ser);
		this.LOGGER.info("amo is " + amo);

		CheckImageUrl image = new CheckImageUrl(acc, ser, amo, dat,
				this.imageUrl);
		String frontImage = image.constructUrl(ImageLocation.FRONT);
		String backImage = image.constructUrl(ImageLocation.BACK);

		this.LOGGER.info("Front Image: " + frontImage);
		this.LOGGER.info("Back Image: " + backImage);

		Boolean isImageAvailable = image.isImageAvailable();
		String resultUrl;
		if (isImageAvailable.booleanValue()) {
			resultUrl = "/redirectSuccess";
			model.addAttribute("front", frontImage);
			model.addAttribute("back", backImage);
		} else {
			resultUrl = "/redirectError";
		}

		return resultUrl;
	}
}