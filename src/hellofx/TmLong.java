package hellofx;

public class TmLong extends TmDat{
	private long lValue;
	
	public TmLong(String name, int num, long time, String dim, int type, long value) {
		super(name, num, time, dim, type);
		lValue = value;
	}

	@Override
	public String getParam() {
		return String.format("%d", lValue);
	}
}
