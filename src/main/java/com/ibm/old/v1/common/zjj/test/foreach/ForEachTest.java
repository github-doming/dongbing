package com.ibm.old.v1.common.zjj.test.foreach;

import c.a.util.core.test.CommTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForEachTest extends CommTest {

	@Test
	public void demo() {
		try {
			jdbcTool = this.findJdbcToolLocal();
			this.transactionStart(jdbcTool);
//			periodDate();
			this.transactionEnd(jdbcTool);
		} catch (Exception e) {
			e.printStackTrace();
			this.transactionRoll(jdbcTool);
		} finally {
			this.transactionClose(jdbcTool);
		}

	}
	public static void main(String[] args) {
		String[] betItem;
		List<Object> list=new ArrayList<>();
		
		String array="冠亚|大#第九名|大#第九名|大";
		String array2="冠亚|大#第九名|大";
		String array3="冠亚|大#第九名|大";
		
		String[] str=array.split("#");
		String[] str2=array2.split("#");
		
		betItem=new String[str.length];
		
		for(int i=0;i<str.length;i++){
			list.add(str[i].concat("#").concat("10"));
			System.out.println(list.get(i));
		}
		
		list.add(str2[0].concat("#").concat("10"));
		System.out.println(list);

	}
	@Test
	public void test02(){
		//java 8
		Arrays.asList( "a", "b", "d" ).forEach(System.out::println);

		Arrays.asList( "a", "b", "d" ).forEach( e -> {
			System.out.print( e );
			System.out.print( e );
		} );

		//Lambda表达式可以引用类成员和局部变量（会将这些变量隐式得转换成final的）
		String separator = ",";
		Arrays.asList( "a", "b", "d" ).forEach(
				( String e ) -> System.out.print( e + separator ) );



	}




}
