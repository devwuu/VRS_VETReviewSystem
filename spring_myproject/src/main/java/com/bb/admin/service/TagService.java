package com.bb.admin.service;

import java.util.ArrayList;

import com.bb.admin.dto.Code;

public interface TagService {

	//태그리스트 출력
	ArrayList<Code> getTagList();

	//태그value 중복 확인
	int checkTagValue(String checkValue);

	//태그 등록
	int insertTag(Code code);

	//태그 삭제
	int deleteTag(String[] codeValue);

}
