package tests.rpgItem;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import application.coordinate.CardinalRotation;
import application.coordinate.Coordinate;
import application.itemWeight.IItemWeight;
import application.itemWeight.ItemWeightKg;
import application.rpgItem.ObservableRpgItem;

public class TestObservableRpgItem {
	ObservableRpgItem emptyItem;
	ObservableRpgItem emptyItemCopy;
	ObservableRpgItem containingItem;
	ObservableRpgItem containingItemCopy;
	ObservableRpgItem containingItemDifferentContents;

	@Before
	public void setupRpgItems() {
		IItemWeight weightA = new ItemWeightKg(0);

		this.emptyItem = new ObservableRpgItem("TestEmptyBag", weightA, new Coordinate(0, 0), CardinalRotation.ZERO);
		this.emptyItemCopy = new ObservableRpgItem("TestEmptyBag", weightA, new Coordinate(0, 0),
				CardinalRotation.ZERO);
		this.containingItem = new ObservableRpgItem("TestNonEmptyBag", "", "", 1, weightA, 1, Arrays.asList(emptyItem),
				new Coordinate(0, 0), CardinalRotation.ZERO, Arrays.asList(new Coordinate(0, 0)),
				Arrays.asList(new Coordinate(0, 0)));
		this.containingItemCopy = new ObservableRpgItem("TestNonEmptyBag", "", "", 1, weightA, 1,
				Arrays.asList(emptyItemCopy), new Coordinate(0, 0), CardinalRotation.ZERO,
				Arrays.asList(new Coordinate(0, 0)), Arrays.asList(new Coordinate(0, 0)));
		this.containingItemDifferentContents = new ObservableRpgItem("TestNonEmptyBag", "", "", 1, weightA, 1,
				Arrays.asList(containingItem), new Coordinate(0, 0), CardinalRotation.ZERO,
				Arrays.asList(new Coordinate(0, 0)), Arrays.asList(new Coordinate(0, 0)));

	}

	@Test
	public void testEqualsBasicSelf() {
		Assert.assertEquals(emptyItem, emptyItem);
	}

	@Test
	public void testEqualsBasicCopy() {
		System.out.println(emptyItem.toString());
		System.out.println(emptyItemCopy.toString());
		Assert.assertEquals(emptyItem, emptyItemCopy);
	}

	@Test
	public void testNotEqualContiainingEmpty() {
		Assert.assertNotEquals(containingItem, emptyItem);
	}

	@Test
	public void testEqualsContainingSelf() {
		Assert.assertEquals(containingItem, containingItem);
	}

	@Test
	public void testEqualsContainingCopy() {
		Assert.assertEquals(containingItem, containingItemCopy);
	}

	@Test
	public void testNotEqualContiainingContents() {
		Assert.assertNotEquals(containingItem, containingItemDifferentContents);
	}
	
	@Test
	public void testGetContentsContainingItem() {
		Assert.assertEquals(containingItem.getContents().size(), 1);
		Assert.assertEquals(containingItem.getContents().get(0), emptyItem);
	}

}
