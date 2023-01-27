
package hellofx;

public class TmCode extends TmDat {
	private int len;
	private String value;
	
	TmCode(String name, int num, long time, String dim, int type, String value, int len) {
		super(name, num, time, dim, type);
		this.len = len;
		this.value = value;
	}
	
	@Override
	public String getParam() {
		return value;
	}
	
	public int getLen() {
		return len;
	}
	
}
