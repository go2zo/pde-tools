package net.jeeeyul.pdetools.shared;

import java.text.MessageFormat;
import java.util.Date;

public class ElapsedTimeLabelProvider {
	public static final long MINITUE = 60 * 1000;
	public static final long HOUR = MINITUE * 60;
	public static final long DAY = HOUR * 24;

	public static final long MONTH = 30;
	public static final long YEAR = 365;

	public String getText(Date time) {
		long today = (System.currentTimeMillis() - (9 * HOUR)) / DAY;
		long thatDay = (time.getTime() - (9 * HOUR)) / DAY;
		long dayElapsed = today - thatDay;

		long timeElapsed = System.currentTimeMillis() - time.getTime();

		if (dayElapsed >= YEAR) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_YEARS_AGO, dayElapsed / YEAR);
		} else if (dayElapsed >= YEAR / 2) {
			return Messages.ElapsedTimeLabelProvider_HALF_YEARS_AGO;
		} else if (dayElapsed >= MONTH) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_MONTHS_AGO, dayElapsed / MONTH);
		} else if (dayElapsed >= 14) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_WEEKS_AGO, dayElapsed / 7);
		} else if (dayElapsed >= 7) {
			return Messages.ElapsedTimeLabelProvider_LAST_WEEK;
		} else if (dayElapsed >= 2) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_DAYS_AGO, dayElapsed);
		} else if (dayElapsed >= 1) {
			return Messages.ElapsedTimeLabelProvider_YESTERDAY;
		}

		else if (timeElapsed > HOUR) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_HOURS_AGO, timeElapsed / HOUR);
		} else if (timeElapsed >= MINITUE) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_MINITUES_AGO, timeElapsed / MINITUE);
		} else if (timeElapsed > 10000) {
			return MessageFormat.format(Messages.ElapsedTimeLabelProvider_SECONDS_AGO, timeElapsed / 1000);
		} else {
			return Messages.ElapsedTimeLabelProvider_JUST_NOW;
		}
	}
}
