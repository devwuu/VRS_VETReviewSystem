package com.bb.user.service;

import java.util.ArrayList;

import org.springframework.stereotype.*;

import com.bb.user.dao.CodeDao;
import com.bb.user.dto.Code;


@Service
public class CodeServiceImpl implements CodeService {

	private final CodeDao cDao = new CodeDao();
	
	@Override
	public ArrayList<Code> getCodeList(String category) {
		return cDao.codeList(category);
	}

}
