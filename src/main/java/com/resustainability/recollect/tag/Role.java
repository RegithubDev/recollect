package com.resustainability.recollect.tag;

import com.resustainability.recollect.commons.Default;
import com.resustainability.recollect.commons.StringUtils;
import com.resustainability.recollect.dto.response.IUserContext;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum Role {
	CUSTOMER(0, "Customer"),
	PROVIDER(1, "Provider"),
	ADMIN(2, "Admin");

	private final int ordinal;
	private final String abbreviation;

	Role(int ordinal, String abbreviation) {
		this.ordinal = ordinal;
		this.abbreviation = abbreviation;
	}

	public int getOrdinal() {
		return ordinal;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public static Role fromAbbreviation(String abbreviation) {
		for (Role role : values()) {
			if (is(abbreviation, role)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Unknown abbreviation: " + abbreviation);
	}

	public static String fromUserContext(IUserContext user) {
		if (null == user) return null;
		if (Boolean.TRUE.equals(user.getIsCustomer())) return CUSTOMER.getAbbreviation();
		if (Boolean.TRUE.equals(user.getIsAdmin())) return ADMIN.getAbbreviation();
		if (Boolean.TRUE.equals(user.getIsProvider())) return PROVIDER.getAbbreviation();
		return null;
	}

	public static boolean is(String abbreviation, Role value) {
		return null != value && StringUtils.isNotBlank(abbreviation) && value
				.getAbbreviation()
				.equalsIgnoreCase(abbreviation.replace(Default.ROLE_PREFIX, Default.EMPTY));
	}

	public static boolean is(String abbreviation, Role... values) {
		return StringUtils.isNotBlank(abbreviation) && Arrays.stream(values)
				.anyMatch(value -> is(abbreviation, value));
	}

	public static List<String> list() {
		return Stream.of(values())
				.map(Role::getAbbreviation)
				.toList();
	}

	@Override
	public String toString() {
		return abbreviation;
	}
}
