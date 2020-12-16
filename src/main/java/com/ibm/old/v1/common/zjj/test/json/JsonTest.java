package com.ibm.old.v1.common.zjj.test.json;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 * @Description:
 * @Author: zjj
 * @Date: 2019-03-25 16:45
 * @Email: 543974681@qq.com
 * @Version: v1.0
 */
public class JsonTest {
	public static void main(String[] args) {
		String str="{\"d\":\"[{\\\"Rows\\\":[{\\\"rowid\\\":1,\\\"idno\\\":714918042,\\\"wagertime\\\":\\\"03-25 16:43:17\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":11,\\\"gamename\\\":\\\"北京赛车\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"731262\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"冠军大小\\\",\\\"wagerobject\\\":\\\"大\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":22.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.18,\\\"winnings\\\":-22.00,\\\"netprofitandloss\\\":-21.82,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":2,\\\"idno\\\":714409269,\\\"wagertime\\\":\\\"03-25 14:59:49\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325024\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"亚军单双\\\",\\\"wagerobject\\\":\\\"单\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":22.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.18,\\\"winnings\\\":21.63,\\\"netprofitandloss\\\":21.81,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":3,\\\"idno\\\":714408193,\\\"wagertime\\\":\\\"03-25 14:59:38\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325024\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"冠军大小\\\",\\\"wagerobject\\\":\\\"大\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":23.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.18,\\\"winnings\\\":-23.00,\\\"netprofitandloss\\\":-22.82,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":4,\\\"idno\\\":714316118,\\\"wagertime\\\":\\\"03-25 14:41:34\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325020\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"第九名大小\\\",\\\"wagerobject\\\":\\\"大\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":20.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.16,\\\"winnings\\\":-20.00,\\\"netprofitandloss\\\":-19.84,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":5,\\\"idno\\\":714316111,\\\"wagertime\\\":\\\"03-25 14:41:34\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325020\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"第三名单双\\\",\\\"wagerobject\\\":\\\"双\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":20.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.16,\\\"winnings\\\":-20.00,\\\"netprofitandloss\\\":-19.84,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":6,\\\"idno\\\":714316065,\\\"wagertime\\\":\\\"03-25 14:41:33\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325020\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"第四名单双\\\",\\\"wagerobject\\\":\\\"单\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":20.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.16,\\\"winnings\\\":-20.00,\\\"netprofitandloss\\\":-19.84,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":7,\\\"idno\\\":714316060,\\\"wagertime\\\":\\\"03-25 14:41:33\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325020\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"冠军单双\\\",\\\"wagerobject\\\":\\\"单\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":20.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.16,\\\"winnings\\\":-20.00,\\\"netprofitandloss\\\":-19.84,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":8,\\\"idno\\\":714315983,\\\"wagertime\\\":\\\"03-25 14:41:32\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325020\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"第六名单双\\\",\\\"wagerobject\\\":\\\"单\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":20.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.16,\\\"winnings\\\":-20.00,\\\"netprofitandloss\\\":-19.84,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":9,\\\"idno\\\":714315960,\\\"wagertime\\\":\\\"03-25 14:41:32\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":21,\\\"gamename\\\":\\\"幸运飞艇\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"20190325020\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"第三名大小\\\",\\\"wagerobject\\\":\\\"大\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":20.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.16,\\\"winnings\\\":-20.00,\\\"netprofitandloss\\\":-19.84,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00},{\\\"rowid\\\":10,\\\"idno\\\":713279014,\\\"wagertime\\\":\\\"03-25 10:15:06\\\",\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"gameno\\\":11,\\\"gamename\\\":\\\"北京赛车\\\",\\\"transdate\\\":\\\"\\\\/Date(1553443200000)\\\\/\\\",\\\"roundno\\\":\\\"731243\\\",\\\"wagerroundno\\\":\\\"A\\\",\\\"wagertypename\\\":\\\"冠军大小\\\",\\\"wagerobject\\\":\\\"大\\\",\\\"wagerodds\\\":\\\"1.9834\\\",\\\"stake\\\":40.00,\\\"commissionrate\\\":0.80,\\\"commission\\\":0.32,\\\"winnings\\\":-40.00,\\\"netprofitandloss\\\":-39.68,\\\"begiveover\\\":false,\\\"hasdetail\\\":0,\\\"sharepercentage\\\":0.00,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00}]},{\\\"Rows\\\":[{\\\"reccount\\\":11,\\\"stake\\\":247.00,\\\"commission\\\":1.98,\\\"winnings\\\":-203.37,\\\"netprofitandloss\\\":-201.39,\\\"percentagestake\\\":0.00,\\\"stakeprofit\\\":0.00}]}]\"}\n";


//		String str="{\"d\":\"[{\\\"Rows\\\":[{\\\"memberno\\\":\\\"qmdb010\\\",\\\"membername\\\":\\\"qmdb010\\\",\\\"opena\\\":true,\\\"openb\\\":false,\\\"openc\\\":false,\\\"opend\\\":false,\\\"opene\\\":false,\\\"credittype\\\":2,\\\"accounttype\\\":1,\\\"creditquota\\\":1000,\\\"usecreditquota\\\":-20.11,\\\"usecreditquota2\\\":4.89,\\\"allowcreditquota\\\":979.89}]},0.0000]\"}";
//		System.out.println(str);
		Integer betNumber=714316065;


		JSONArray jsonArray= JSONArray.fromObject(JSONObject.fromObject(str).get("d"));

		jsonArray.getJSONObject(0).getJSONArray("Rows");

		System.out.println(jsonArray.getJSONObject(0).get("Rows"));
//		JSONObject jsonObject=jsonArray.getJSONObject(0);

//		System.out.println(JSONObject.fromObject(str).get("d"));


//		JSONObject jsonObject1;
//		for(Object json:jsonObject.getJSONArray("Rows")){
//
//			jsonObject1=JSONObject.fromObject(json);
//
//			if(jsonObject1.getInt("idno")==betNumber){
//				System.out.println(jsonObject1);
//			}
//		}


	}
}
