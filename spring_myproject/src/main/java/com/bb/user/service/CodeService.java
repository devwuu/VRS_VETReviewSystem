package com.bb.user.service;

import java.util.ArrayList;

import com.bb.user.dto.Code;


public interface CodeService {
	
	public ArrayList<Code> getCodeList(String category);

	
}
