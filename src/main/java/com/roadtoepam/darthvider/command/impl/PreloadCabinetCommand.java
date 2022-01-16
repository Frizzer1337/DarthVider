package com.roadtoepam.darthvider.command.impl;

import static com.roadtoepam.darthvider.command.PageRouting.*;
import static com.roadtoepam.darthvider.command.RequestContent.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

import com.roadtoepam.darthvider.command.Command;
import com.roadtoepam.darthvider.command.Router;
import com.roadtoepam.darthvider.entity.ConnectedTariff;
import com.roadtoepam.darthvider.entity.User;
import com.roadtoepam.darthvider.entity.UserContract;
import com.roadtoepam.darthvider.entity.UserInfo;
import com.roadtoepam.darthvider.exception.CommandException;
import com.roadtoepam.darthvider.exception.ServiceException;
import com.roadtoepam.darthvider.service.ClientService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class PreloadCabinetCommand implements Command{
	
	private ClientService clientService;

    public PreloadCabinetCommand(ClientService clientService){
        this.clientService = clientService;
    }

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		
		HttpSession session = request.getSession();
		
		String email = (String)session.getAttribute(EMAIL);
		Optional<User> user = Optional.empty();
		Optional<UserInfo> userInfo = Optional.empty();
		Optional<UserContract> userContract = Optional.empty();
		Optional<ConnectedTariff> userTariff = Optional.empty();
		try {
			user = clientService.getUserByEmail(email);
			userInfo = clientService.getUserInfoByEmail(email);
			userContract = clientService.getUserContractByEmail(email);
			userTariff = clientService.getUserTariffByEmail(email);
		} catch (ServiceException e) { 
			throw new CommandException("Error occured while getting user info",e);
		}
		if(user.isPresent() && userInfo.isPresent() && userContract.isPresent() && userTariff.isPresent()) {
			User userData = user.get();
			UserInfo userInfoData = userInfo.get();
			UserContract userContractData = userContract.get();
			ArrayList<Integer> userTariffData = userTariff.get()
														  .getContractInfo()
														  .get((int)userContractData.getIdContract());
			session.setAttribute(LOGIN,userData.getLogin());
			session.setAttribute(NAME, userInfoData.getName());
			session.setAttribute(SURNAME, userInfoData.getSurname());
			session.setAttribute(PHONE, userInfoData.getPhone());
			session.setAttribute(CITY, userInfoData.getCity());
			session.setAttribute(PASSWORD, "****");
			session.setAttribute(CONTRACTID, userContractData.getIdContract());
			session.setAttribute(CONTRACTSTART, userContractData.getStartDate());
			session.setAttribute(CONTRACTEND, userContractData.getEndDate());
			session.setAttribute(CONTRACTDISCOUNT, userContractData.getDiscount());
			session.setAttribute(TARIFFS, userTariffData);
		}
		
		session.setAttribute(CABINET_EXIST, "CABINET_LOADED");
		
		return new Router(CABINET_PAGE, Router.RouterType.REDIRECT);
	}

}
