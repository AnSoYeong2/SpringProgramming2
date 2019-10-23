package com.mycompany.web.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycompany.web.dto.Ch10Board;
@Component
public class Ch10BoardDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public List<Ch10Board> selectList(int startRowNo, int endRowNo) {
		Map<String, Integer> map = new HashMap<>();
		map.put("startRowNo", startRowNo);
		map.put("endRowNo", endRowNo);
		List<Ch10Board> boardList = sqlSessionTemplate.selectList("selectList", map);
		return boardList;
	}
	
	public int selectTotalRowNum() {
		int totalRowNum = sqlSessionTemplate.selectOne("board.selectTotalRowNum");
		return totalRowNum;
	}
	
	public int insert(Ch10Board board) {
		//insert delete update는 실제 반영된 행 수를 리턴한다 따라서 리턴값을 int로 준다
		//insert를 실행하기 전에 몇개인지 알 수 없어 어디서 알수있어? mapper파일에서
		int rows = sqlSessionTemplate.insert("board.insert", board);
		return rows;
	}

	public Ch10Board selectBoard(int bno) {
		Ch10Board board = sqlSessionTemplate.selectOne("board.selectBoard", bno);
		return board;
	}

	public int updateHitcount(int bno) {
		//업데이트 된 조회수 
		int rows = sqlSessionTemplate.update("board.updateHitcount", bno);
		return rows;
	}

	public int updateBoard(Ch10Board board) {
		int rows = sqlSessionTemplate.update("board.updateBoard", board);
		return rows;
	}

	public int deleteBoard(int bno) {
		int rows = sqlSessionTemplate.delete("board.deleteBoard", bno);
		return rows;
	}
}
