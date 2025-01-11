package com.transline.vms.utils;

import java.util.Random;

public class ShortUniqueIdGenerator {
	private static final Random RANDOM = new Random();

	public static String generateShortUniqueId() {
		long currentTime = System.currentTimeMillis();
		int randomInt = RANDOM.nextInt(1000);
		return String.format("%d-%03d", currentTime, randomInt);
	}
}
