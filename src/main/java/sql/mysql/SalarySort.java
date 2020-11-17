package sql.mysql;

import java.util.ArrayList;
import java.util.HashMap;

import pojo.PublicEntity;
import utility.ConnDbUtil;
import utility.FormatUtil;

/**
 * <p>Employee 表包含所有员工信息，每个员工有其对应的工号 Id，姓名 Name，工资 Salary 和部门编号 DepartmentId 。
    +----+-------+--------+--------------+
    | Id | Name  | Salary | DepartmentId |
    +----+-------+--------+--------------+
    | 1  | Joe   | 85000  | 1            |
    | 2  | Henry | 80000  | 2            |
    | 3  | Sam   | 60000  | 2            |
    | 4  | Max   | 90000  | 1            |
    | 5  | Janet | 69000  | 1            |
    | 6  | Randy | 85000  | 1            |
    | 7  | Will  | 70000  | 1            |
    +----+-------+--------+--------------+
    Department 表包含公司所有部门的信息。
    +----+----------+
    | Id | Name     |
    +----+----------+
    | 1  | IT       |
    | 2  | Sales    |
    +----+----------+
           编写一个 SQL 查询，找出每个部门获得前三高工资的所有员工。例如，根据上述给定的表，查询结果应返回：
    +------------+----------+--------+
    | Department | Employee | Salary |
    +------------+----------+--------+
    | IT         | Max      | 90000  |
    | IT         | Randy    | 85000  |
    | IT         | Joe      | 85000  |
    | IT         | Will     | 70000  |
    | Sales      | Henry    | 80000  |
    | Sales      | Sam      | 60000  |
    +------------+----------+--------+</p>
 * @author 何杰
 * @date 2020-8-5
 * @version 1.0
 * @since JDK 1.8
 */
public class SalarySort {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ArrayList<String> columns = new ArrayList<String>();
		columns.add("Department");
		columns.add("Employee");
		columns.add("Salary");
		String sql = "select b.name as Department, a.name as Employee, a.salary as Salary from employee a, department b where a.departmentid = b.id and (select count(distinct(salary)) from employee where salary > a.salary and departmentid = a.departmentid) < 3 order by b.id, a.salary desc";
		HashMap<String, Object> result = ConnDbUtil.executeSql(sql, new ArrayList<String>(), "select", columns);
		
		if (FormatUtil.convertString(result.get("returnCode"), "").equals("0000")) {
			System.out.println("+------------+----------+--------+");
			System.out.println("| Department | Employee | Salary |");
			System.out.println("+------------+----------+--------+");
			for (PublicEntity publicEntity : (ArrayList<PublicEntity>)result.get("dataSet")) {
				System.out.println("| " + publicEntity.getData1() + " | " + publicEntity.getData2() + " | " + publicEntity.getData3() + " |");
			}
			System.out.println("+------------+----------+--------+");
		}

	}

}
