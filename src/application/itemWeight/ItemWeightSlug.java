package application.itemWeight;

public class ItemWeightSlug extends ItemWeightBase {
	private final static float SL_PER_KG = (float) 0.06852177;
	public ItemWeightSlug(float weight_sl) {
		super("slugs", "sl", SL_PER_KG, weight_sl/SL_PER_KG);
	}

	static ItemWeightSlug fromKg(float weight_sl) {
		return new ItemWeightSlug(weight_sl * SL_PER_KG);
	}
}

