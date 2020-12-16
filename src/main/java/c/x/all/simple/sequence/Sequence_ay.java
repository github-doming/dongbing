package c.x.all.simple.sequence;
public class Sequence_ay {
	/**
	 * 最大数
	 */
	private int max = 5;
	/**
	 * 生成序列;
	 * 
	 * 如果已有数字是1,2,5;
	 * 
	 * 则返回３;
	 * 
	 * 如果已有数字是2,3,1,4,5,;
	 * 
	 * 则返回第一个数字2;
	 * 
	 * @param numbers
	 * @return
	 */
	public String genNubmer(String numbers) {
		String[] array_numbers = numbers.split(",");
		int int_number = 0;
		String return_number = null;
		for (int i = 1; i <= max; i++) {
			// 是否新值
			boolean is_new = true;
			for (String number : array_numbers) {
				if (number.equals(String.valueOf(i))) {
					is_new = false;
				}
			}
			if (is_new) {
				// 得到新值
				int_number = i;
				break;
			} else {
				continue;
			}
		}
		System.out.println("得到数字序列=" + int_number);
		if (int_number == 0) {
			return_number = array_numbers[0];
		} else {
			return_number = String.valueOf(int_number);
		}
		return return_number;
	}
}
