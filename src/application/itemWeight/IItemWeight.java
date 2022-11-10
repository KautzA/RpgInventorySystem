package application.itemWeight;

public interface IItemWeight {
	// public static ItemWeight fromKg(float weightKg);
	
	/**
	 * Get the weight converted to kg
	 */
	public float toKg();
	
	/**
	 * Get the weight in the units given by getUnits
	 */
	public float getValue();
	
	/**
	 * Get the units of this weight
	 * (such as kilograms or pounds)
	 */
	public String getUnits();
	
	/**
	 * Get the abbreviated units of this weight
	 * (such as "kg" or "lb")
	 */
	public String getUnitsAbbreviation();
	
	/**
	 * Add the other weight to this one and return a new weight
	 * with the same units as this one
	 * @param other the weight to add to this one
	 * @return the new weight
	 */
	public IItemWeight add(IItemWeight other);

	/**
	 * Return a new weight by scaling this one with the same units
	 * @param scaleFactor the number to multiply by
	 * @return the new weight
	 */
	public IItemWeight scale(float scaleFactor);
	
	/*
	 * Checks for equality of the converted value
	 * @param other the weight to compare to
	 */
	public boolean equals(IItemWeight other);
	
	/*
	 * Checks for equality of the other weight to this one
	 * including comparing the units and abbreviation
	 * @param other the weight to compare to
	 */
	public boolean equalsWithUnits(IItemWeight other);
	
}
