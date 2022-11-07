package application.itemWeight;

public class ItemWeightLb extends ItemWeightBase {
	private final static float LBS_PER_KG = (float) 2.20462262;
	public ItemWeightLb(float weight_lbs) {
		super("pounds", "lb", LBS_PER_KG, weight_lbs/LBS_PER_KG);
	}

	static ItemWeightLb fromKg(float weightKg) {
		return new ItemWeightLb(weightKg * LBS_PER_KG);
	}
}
