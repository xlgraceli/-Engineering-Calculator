/**
 * This is the Formula class that stores and calculates variables
 */
public class Formula{
	/** r is a double value.*/
	private double r = 0;
	/** w is a double calculated value.*/
	private double w = 0;
	/** changeMax is a double calculated value.*/
	private double changeMax = 0;
	/** mMax is a double calculated value.*/
	private double mMax = 0;
	/** length is a double beam length.*/
	private double length = 0;
	/** exi is a double E x I value.*/
	private double exi = 0;
	/** dl is a double dead load value.*/
	private double dl = 0;
	/** ll is a double live load value.*/
	private double ll = 0;
	/** caseNum is an integer case number.*/
	private int caseNum = 0; 
	/** modelName is a String model name.*/
	private String modelName = "";
	
	/**
	 * This constructor sets the saved values into this class' variables. 
	 * @param length is a double
	 * @param exi is a double
	 * @param dl is a double
	 * @param ll is a double
	 * @param caseNum is an integer
	 * @param name is a string
	 */
	public Formula(double length, double exi, double dl, double ll, int caseNum, String name) {
		this.length = length;
		this.exi = exi;
		this.dl = dl;
		this.ll = ll;
		this.caseNum = caseNum;
		this.modelName = name;
	}
	
	/**
	 * This is the getter method for beam length.
	 * @return length
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * This is the getter method for E x I.
	 * @return exi
	 */
	public double getExI() {
		return exi;
	}
	
	/**
	 * This is the getter method for the model name.
	 * @return modelName
	 */
	public String getName() {
		return modelName;
	}
	
	/**
	 * This is the getter method for the dead load.
	 * @return dl
	 */
	public double getDead() {
		return dl;
	}
	
	/**
	 * This is the getter method for the live load.
	 * @return ll
	 */
	public double getLive() {
		return ll;
	}
	
	/**
	 * This is the getter method for the case number.
	 * @return caseNum
	 */
	public int getCase() {
		return caseNum;
	}
	
	/**
	 * This is the calculate method for W, which is dependent on the case number the user selects.
	 * @return w
	 */
	public double calcW() {
		if (caseNum == 1) {
			w = 1*dl + 1*ll;
		}else {
			w = 1.25*dl + 1.5*ll;
		}
		return w;
	}
	
	/**
	 * This is the calculate method for the resistance at the ends of the beam, which is dependent on the W calculated.
	 * @return r
	 */
	public double calcR() {
		r = calcW() * length /2;
		return r;
	}
	
	/**
	 * This is the calculate method for the maximum change in deflection.
	 * @return changeMax
	 */
	public double calcChangeMax() {
		changeMax = (5*calcW()*Math.pow(length, 4)*Math.pow(10, 12))/(384*exi);
		return changeMax;
	}
	
	/**
	 * This is the calculate method for the maximum moment.
	 * @return mMax
	 */
	public double calcMmax() {
		mMax = (calcW()*Math.pow(length, 2))/8;
		return mMax;
	}
	
}
