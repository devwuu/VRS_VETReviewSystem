package com.bb.admin.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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

}
