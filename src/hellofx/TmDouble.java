package hellofx;

public class TmDouble extends TmDat{
	private double dValue;

	TmDouble(String name, int num, long time, String dim, int type, double value) {
		super(name, num, time, dim, type);
		dValue = value;
	}
	
	@Override
	public String getParam() {
		return Double.toString(dValue);
	}
}
