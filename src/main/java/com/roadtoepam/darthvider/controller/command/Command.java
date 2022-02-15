package com.roadtoepam.darthvider.controller.command;


import com.roadtoepam.darthvider.exception.CommandException;

import jakarta.servlet.http.HttpServletRequest;

public interface Command {
	
	Router execute(HttpServletRequest request) throws CommandException;


}
