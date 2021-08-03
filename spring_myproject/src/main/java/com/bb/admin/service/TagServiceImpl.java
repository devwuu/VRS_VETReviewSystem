package com.bb.admin.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.bb.admin.dao.TagDao;
import com.bb.admin.dto.Code;

@Service
public class TagServiceImpl implements TagService {

	@Autowired
	TagDao td = new TagDao();
	
	@Override
	public ArrayList<Code> getTagList() {
		
		return td.getTagList();
	}

	@Override
	public int checkTagValue(String checkValue) {
		
		return td.checkTagValue(checkValue);
	}

	@Override
	public int insertTag(Code code) {
		return td.insertTag(code);
	}

	@Override
	public int deleteTag(String[] codeValue) {
		return td.deleteTage(codeValue);
	}

}
