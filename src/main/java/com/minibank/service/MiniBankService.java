package com.minibank.service;

import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.put;

import com.google.gson.Gson;
import com.minibank.controller.MiniBankController;
import com.minibank.model.response.ApiResponse;

public class MiniBankService {
	public static void main(String[] args) {
		port(7777);
		
		get ("mini-bank/app/ping", (request, response) -> {
			return "pong";
		});
		
		post ( "mini-bank/app/account/create", (request, response) -> {
			response.type("application/json");
			String accName = request.queryParams("accountName");
			String accAddr = request.queryParams("accountAddress");
			String currency = request.queryParams("currency");
			ApiResponse apiResponse = MiniBankController.getInstance().createAccount(accName, accAddr, currency);
			Gson gson = new Gson ();
			return gson.toJson(apiResponse);
		});
		
		put("movie-rental/app/account/deposit", (request, response) -> {
			response.type("application/json");
			String accountName = request.queryParams("accountName");
			float money = new Float(request.queryParams("money"));
			ApiResponse apiResponse = MiniBankController.getInstance().withdrawMoney(accountName, money);
			Gson gson = new Gson ();
			return gson.toJson(apiResponse);
		});
		
		get("movie-rental/app/account/balance", (request, response) -> {
			response.type("application/json");
			String accountName = request.queryParams("accountName");
			ApiResponse apiResponse = MiniBankController.getInstance().checkBalance(accountName);
			Gson gson = new Gson ();
			return gson.toJson(apiResponse);
		});
		
		delete("movie-rental/app/account", (request, response) -> {
			response.type("application/json");
			String accountName = request.queryParams("accountName");
			ApiResponse apiResponse = MiniBankController.getInstance().closeAccount(accountName);
			Gson gson = new Gson ();
			return gson.toJson(apiResponse);
		});
		
	}
}
