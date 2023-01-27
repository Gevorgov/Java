
package hellofx;

public class TmPoint extends TmDat{
	int len;
	
	public TmPoint(String name, int num, long time, String dim, int type, int len) {
		super(name, num, time, dim, type);
		this.len = len;
	}
	
	@Override
	public String getParam() {
		return String.format("%d", len);
	}
}
