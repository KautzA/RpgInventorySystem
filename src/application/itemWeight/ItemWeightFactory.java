package application.itemWeight;

public class ItemWeightFactory {
	public static IItemWeight GetWeight(String weightUnit, float weightValue) {
		switch (weightUnit.toLowerCase()) {
		case("kg"):
		case("kgs"):
		case("kilogram"):
		case("kilograms"):
			return new ItemWeightKg(weightValue);
		case("lb"):
		case("lbs"):
		case("pound"):
		case("pounds"):
			return new ItemWeightLb(weightValue);
		case("sl"):
		case("slug"):
		case("slugs"):
			return new ItemWeightSlug(weightValue);
		default:
			return new ItemWeightKg(weightValue);
		
		}
	}
}
