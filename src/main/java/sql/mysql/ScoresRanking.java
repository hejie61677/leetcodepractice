package sql.mysql;

import java.util.ArrayList;
import java.util.HashMap;

import pojo.PublicEntity;
import utility.ConnDbUtil;
import utility.FormatUtil;

/**
 * <p>编写一个 SQL 查询来实现分数排名。
	如果两个分数相同，则两个分数排名（Rank）相同。请注意，平分后的下一个名次应该是下一个连续的整数值。换句话说，名次之间不应该有“间隔”。
	+----+-------+
	| Id | Score |
	+----+-------+
	| 1  | 3.50  |
	| 2  | 3.65  |
	| 3  | 4.00  |
	| 4  | 3.85  |
	| 5  | 4.00  |
	| 6  | 3.65  |
	+----+-------+
	例如，根据上述给定的?Scores 表，你的查询应该返回（按分数从高到低排列）：
	
	+-------+------+
	| Score | Rank |
	+-------+------+
	| 4.00  | 1    |
	| 4.00  | 1    |
	| 3.85  | 2    |
	| 3.65  | 3    |
	| 3.65  | 3    |
	| 3.50  | 4    |
	+-------+------+</p>
 * @author 何杰
 * @date 2020-8-4
 * @version 1.0
 * @since JDK 1.8
 */
public class ScoresRanking {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("Score");
		columns.add("Rank");
		String sql = "SELECT Score, IFNULL((SELECT COUNT(DISTINCT SCORE) FROM SCORES WHERE SCORE > T1.SCORE) , 0) + 1 AS `Rank` FROM SCORES T1 ORDER BY SCORE DESC";
		HashMap<String, Object> result = ConnDbUtil.executeSql(sql, new ArrayList<String>(), "select", columns);
		
		if (FormatUtil.convertString(result.get("returnCode"), "").equals("0000")) {
			System.out.println("| Score | Rank |");
			System.out.println("+-------+------+");
			for (PublicEntity publicEntity : (ArrayList<PublicEntity>)result.get("dataSet")) {
				System.out.println("| " + publicEntity.getData1() + "  | " + publicEntity.getData2() + "    |");
			}
			System.out.println("+-------+------+");
		}
	}

}
