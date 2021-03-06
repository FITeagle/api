package org.fiteagle.api.core.test;

import java.util.Calendar;
import java.util.Date;

import org.fiteagle.api.core.TimeHelperMethods;
import org.fiteagle.api.core.TimeParsingException;
import org.junit.Assert;
import org.junit.Test;

import com.hp.hpl.jena.datatypes.xsd.XSDDateTime;

public class TimeTest {

	@Test
	public void test_getTimeFromString() throws TimeParsingException {

		String s = "2016-01-06T16:03:24";
		Date date = TimeHelperMethods.getTimeFromString(s);
		String dateTime = TimeHelperMethods.getStringFromTime(date);
		// System.out.println(dateTime);

		String s1 = "2016-01-06T16:03:24Z";
		Date date1 = TimeHelperMethods.getTimeFromString(s1);
		String dateTime1 = TimeHelperMethods.getStringFromTime(date1);
		// System.out.println(dateTime1);

		String s2 = "2016-01-26T15:32:28+01:00";
		Date date2 = TimeHelperMethods.getTimeFromString(s2);
		String dateTime2 = TimeHelperMethods.getStringFromTime(date2);
		// System.out.println(dateTime2);

		String s3 = "2016-01-26T15:32:28+03:00";
		Date date3 = TimeHelperMethods.getTimeFromString(s3);
		String dateTime3 = TimeHelperMethods.getStringFromTime(date3);
		// System.out.println(dateTime3);

		String s4 = "2016-01-26T15:32:28+0300";
		Date date4 = TimeHelperMethods.getTimeFromString(s4);
		String dateTime4 = TimeHelperMethods.getStringFromTime(date4);
		System.out.println(dateTime4);
	}

	@Test
	public void test_getStringFromTime() throws TimeParsingException {

		Date date = new Date();
		String dateString = TimeHelperMethods.getStringFromTime(date);
		// System.out.println(dateString);
	}

	@Test
	public void test_getStringFromXSDTime() throws TimeParsingException {

		Calendar date = Calendar.getInstance();
		XSDDateTime timeXSDDateTime = new XSDDateTime(date);
		String dateString = TimeHelperMethods.getStringFromTime(timeXSDDateTime);
		// System.out.println(dateString);
	}

	@Test(expected = TimeParsingException.class)
	public void test_getTimeFromString_exception() throws TimeParsingException {

		String s = "2016-01-06sfdT16:03:24";
		TimeHelperMethods.getTimeFromString(s);
	}

	@Test
	public void test_date1SameOrAfterDate2() throws TimeParsingException {

		String s1 = "2016-01-06T16:05:24";
		Date date1 = TimeHelperMethods.getTimeFromString(s1);
		String s2 = "2016-01-06T16:03:24";
		Date date2 = TimeHelperMethods.getTimeFromString(s2);

		Assert.assertTrue(TimeHelperMethods.date1SameOrAfterDate2(date1, date2));

		String s3 = "2016-02-06T16:03:24";
		Date date3 = TimeHelperMethods.getTimeFromString(s3);
		String s4 = "2016-01-06T16:03:24";
		Date date4 = TimeHelperMethods.getTimeFromString(s4);

		Assert.assertTrue(TimeHelperMethods.date1SameOrAfterDate2(date3, date4));

		String s5 = "2016-01-06T16:03:24";
		Date date5 = TimeHelperMethods.getTimeFromString(s5);
		String s6 = "2016-01-06T16:05:24";
		Date date6 = TimeHelperMethods.getTimeFromString(s6);

		Assert.assertFalse(TimeHelperMethods.date1SameOrAfterDate2(date5, date6));
		Assert.assertTrue(TimeHelperMethods.date1SameOrAfterDate2(date6, date5));

		// Same time
		String s7 = "2016-01-26T16:00:00+02:00"; // Germany
		Date date7 = TimeHelperMethods.getTimeFromString(s7);
		String s8 = "2016-01-26T15:00:00+01:00"; // London
		Date date8 = TimeHelperMethods.getTimeFromString(s8);
		Assert.assertTrue(TimeHelperMethods.date1SameOrAfterDate2(date7, date8));

		// Germany after London
		String s9 = "2016-01-26T16:01:00+02:00"; // Germany
		Date date9 = TimeHelperMethods.getTimeFromString(s9);
		String dateTime9 = TimeHelperMethods.getStringFromTime(date9);
		// System.out.println(dateTime9);
		String s10 = "2016-01-26T15:00:00+01:00"; // London
		Date date10 = TimeHelperMethods.getTimeFromString(s10);
		String dateTime10 = TimeHelperMethods.getStringFromTime(date10);
		// System.out.println(dateTime10);
		Assert.assertTrue(TimeHelperMethods.date1SameOrAfterDate2(date9, date10));
		Assert.assertFalse(TimeHelperMethods.date1SameOrAfterDate2(date10, date9));
	}

	@Test
	public void test_timesOverlap() throws TimeParsingException {
		String s1 = "2016-01-06T16:03:24";
		String e1 = "2016-01-06T16:05:24";
		String s2 = "2016-01-06T16:02:24";
		String e2 = "2016-01-06T16:04:24";

		Assert.assertTrue(TimeHelperMethods.timesOverlap(s1, e1, s2, e2));
		Assert.assertTrue(TimeHelperMethods.timesOverlap(s2, e2, s1, e1));
		Assert.assertTrue(TimeHelperMethods.timesOverlap(s2, e1, s1, e2));
		Assert.assertTrue(TimeHelperMethods.timesOverlap(s1, e2, s2, e1));
		Assert.assertFalse(TimeHelperMethods.timesOverlap(s2, s1, e2, e1));
		Assert.assertFalse(TimeHelperMethods.timesOverlap(e2, e1, s2, s1));

		String s3 = "2016-01-06T16:03:24";
		String e3 = "2016-01-06T16:03:25";
		String s4 = "2016-01-06T16:03:24";
		String e4 = "2016-01-06T16:03:26";

		Assert.assertTrue(TimeHelperMethods.timesOverlap(s3, e3, s4, e4));
		Assert.assertTrue(TimeHelperMethods.timesOverlap(s4, e4, s3, e3));
		Assert.assertTrue(TimeHelperMethods.timesOverlap(s4, s4, s3, s3));
		Assert.assertFalse(TimeHelperMethods.timesOverlap(s3, s3, e3, e3));
		Assert.assertFalse(TimeHelperMethods.timesOverlap(e3, e3, s3, s3));
	}

	@Test
	public void test_dateNotInPast() throws TimeParsingException {
		String s1 = "2056-01-06T16:03:24";
		Date d1 = TimeHelperMethods.getTimeFromString(s1);

		Assert.assertTrue(TimeHelperMethods.dateNotInPast(d1));

		String s2 = "2016-01-06T16:03:24";
		Date d2 = TimeHelperMethods.getTimeFromString(s2);

		Assert.assertFalse(TimeHelperMethods.dateNotInPast(d2));
	}
}