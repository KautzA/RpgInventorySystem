package application.itemWeight;

public class ItemWeightKg implements IItemWeight {
	
	final float weightKg;
	
	public ItemWeightKg(float weightKg) {
		this.weightKg = weightKg;
	}
	
	static ItemWeightKg fromKg(float weightKg) {
		return new ItemWeightKg(weightKg);
	}

	@Override
	public float toKg() {
		return this.weightKg;
	}

	@Override
	public float getValue() {
		return this.weightKg;
	}

	@Override
	public String getUnits() {
		if (this.weightKg == 1) {
			return "kilogram";
		} else {
			return "kilograms";
		}
	}

	@Override
	public String getUnitsAbbreviation() {
		return "kg";
	}

	@Override
	public IItemWeight scale(float scaleFactor) {
		return new ItemWeightKg(this.weightKg * scaleFactor);
	}

	@Override
	public IItemWeight add(IItemWeight other) {
		return new ItemWeightKg(this.weightKg + other.toKg());
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
