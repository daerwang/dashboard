package com.oceanbank.webapp.redirector;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CheckImageUrl {
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	// private static final String USER_AGENT = "Mozilla/5.0";
	private String imageUrl;
	private String account;
	private String serial;
	private String amount;
	private String date;

	public CheckImageUrl() {
	}

	public CheckImageUrl(String account, String serial, String amount,
			String date, String imageUrl) {
		this.account = account;
		this.serial = serial;
		this.amount = amount;
		this.date = date;
		this.imageUrl = imageUrl;
	}

	public Boolean isImageAvailable() {
		if (this.account == null) {
			return Boolean.valueOf(false);
		}

		String frontImageUrl = constructUrl(ImageLocation.FRONT);
		String backImageUrl = constructUrl(ImageLocation.BACK);

		int responseFront = checkUrlResponseCode(frontImageUrl);
		int responseBack = checkUrlResponseCode(backImageUrl);
		boolean isImageThere;

		if ((responseFront == 200) || (responseBack == 200))
			isImageThere = true;
		else {
			isImageThere = false;
		}

		return Boolean.valueOf(isImageThere);
	}

	public String constructUrl(ImageLocation location) {
		String code = ImageLocation.FRONT == location ? "F" : "B";

		StringBuilder front = new StringBuilder();
		front.append(this.imageUrl + "?StreamImage?Date=");
		front.append(this.date);
		front.append("&BLDBK=");
		front.append(this.account);
		front.append("&Serial=");
		front.append(this.serial);
		front.append("&Amount=");
		front.append(this.amount);
		front.append("&ImageFB=" + code + "&ImageHeight=200&ImageWidth=550");

		return front.toString();
	}

	private int checkUrlResponseCode(String url) {
		int responseCode = 404;
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("HEAD");
			con.setRequestProperty("User-Agent", "Mozilla/5.0");
			responseCode = con.getResponseCode();
			this.LOGGER.info("Response Code is " + responseCode);
		} catch (IOException e) {
			this.LOGGER.error("An error occured in accessing url " + url);
			return responseCode;
		}

		return responseCode;
	}

	public String getAccount() {
		return this.account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getSerial() {
		return this.serial;
	}

	public void setSerial(String serial) {
		this.serial = serial;
	}

	public String getAmount() {
		return this.amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDate() {
		return this.date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getImageUrl() {
		return this.imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}