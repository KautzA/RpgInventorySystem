package application.itemWeight;

public class ItemWeightBase implements IItemWeight {
	
	final String unitsName;
	final String unitsAbbreviation;
	final float weightKg;
	final float unitsPerKg;
	
	ItemWeightBase(String name,
				   String abbreviation,
                   float unitsPerKg,
                   float weightKg) {
		this.unitsName = name;
		this.unitsAbbreviation = abbreviation;
		this.unitsPerKg = unitsPerKg;
		this.weightKg = weightKg;
	}

	@Override
	public float toKg() {
		return this.weightKg;
	}

	@Override
	public float getValue() {
		return this.weightKg * unitsPerKg;
	}

	@Override
	public String getUnits() {
		return this.unitsName;
	}

	@Override
	public String getUnitsAbbreviation() {
		return this.unitsAbbreviation;
	}

	@Override
	public IItemWeight add(IItemWeight other) {
		return new ItemWeightBase(this.unitsName,
				                  this.unitsAbbreviation,
				                  this.unitsPerKg,
				                  this.weightKg + other.toKg());
	}

	@Override
	public IItemWeight scale(float scaleFactor) {
		return new ItemWeightBase(this.unitsName,
				                  this.unitsAbbreviation,
                                  this.unitsPerKg,
                                  this.weightKg * scaleFactor);
	}

	@Override
	public boolean equals(IItemWeight other) {
		return this.toKg() == other.toKg();
	}

	@Override
	public boolean equalsWithUnits(IItemWeight other) {
		return this.equals(other)
            && this.getUnits().equals(other.getUnits())
            && this.getUnitsAbbreviation().equals(other.getUnitsAbbreviation());
	}
}
