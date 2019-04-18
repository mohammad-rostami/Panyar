package in.public_static.carin.paniar.calender;


/**
 * DateConverter is a utility class for converting between dates of different
 * calendars. This is carried out by various conversion methods provided by this
 * class.
 *
 * @author Amir
 */

/*
 * Functions in this class are translated from a VB code I sometime found
 * somewhere in the internet. I can't remember where it was. Anyway I thank
 * him/her greatly!
 */

public final class DateConverter {

    public static final IslamicDate civilToIslamic(CivilDate civil) {
        return jdnToIslamic(civilToJdn(civil));
    }

    public static final long civilToJdn(CivilDate civil) {

        long lYear = civil.getYear();
        long lMonth = civil.getMonth();
        long lDay = civil.getDayOfMonth();

        if ((lYear > 1582) || ((lYear == 1582) && (lMonth > 10))
                || ((lYear == 1582) && (lMonth == 10) && (lDay > 14))) {
            return ((1461 * (lYear + 4800 + ((lMonth - 14) / 12))) / 4)
                    + ((367 * (lMonth - 2 - 12 * (((lMonth - 14) / 12)))) / 12)
                    - ((3 * (((lYear + 4900 + ((lMonth - 14) / 12)) / 100))) / 4)
                    + lDay - 32075;
        } else
            return julianToJdn(lYear, lMonth, lDay);

    }

    public static final PersianDate civilToPersian(CivilDate civil) {
        return jdnToPersian(civilToJdn(civil));
    }

    private static final long floor(double d) {
        return (long) Math.floor(d);
    }

    public static final CivilDate islamicToCivil(IslamicDate islamic) {
        return jdnToCivil(islamicToJdn(islamic));
    }

    public static final long islamicToJdn(IslamicDate islamic) {
        // NMONTH is the number of months between julian day number 1 and
        // the year 1405 A.H. which started immediatly after lunar
        // conjunction number 1048 which occured on September 1984 25d
        // 3h 10m UT.
        int NMONTHS = (1405 * 12 + 1);
        int year = islamic.getYear();
        int month = islamic.getMonth();
        int day = islamic.getDayOfMonth();

        if (year < 0)
            year++;

        long k = month + year * 12 - NMONTHS; // nunber of months since 1/1/1405
        return floor(visibility(k + 1048) + day + 0.5);
    }

    public static final PersianDate islamicToPersian(IslamicDate islamic) {
        return jdnToPersian(islamicToJdn(islamic));
    }

    public static final CivilDate jdnToCivil(long jdn) {

        if (jdn > 2299160) {
            long l = jdn + 68569;
            long n = ((4 * l) / 146097);
            l = l - ((146097 * n + 3) / 4);
            long i = ((4000 * (l + 1)) / 1461001);
            l = l - ((1461 * i) / 4) + 31;
            long j = ((80 * l) / 2447);
            int day = (int) (l - ((2447 * j) / 80));
            l = (j / 11);
            int month = (int) (j + 2 - 12 * l);
            int year = (int) (100 * (n - 49) + i + l);
            return new CivilDate(year, month, day);
        } else
            return jdnToJulian(jdn);
    }

    public static final IslamicDate jdnToIslamic(long jd) {

        /*CivilDate civil = jdnToCivil(jd);

        int year = civil.getYear();
        int month = civil.getMonth();
        int day = civil.getDayOfMonth();

        long k = floor(0.6 + (year + (month % 2 == 0 ? month : month - 1) / 12d
                + day / 365f - 1900) * 12.3685);

        double mjd;
        do {
            mjd = visibility(k);
            k = k - 1;
        } while (mjd > (jd - 0.5));

        k = k + 1;
        long hm = k - 1048;

        year = 1405 + (int) (hm / 12);
        month = (int) (hm % 12) + 1;

        if (hm != 0 && month <= 0) {
            month = month + 12;
            year = year - 1;
        }

        if (year <= 0)
            year = year - 1;

        day = (int) floor(jd - mjd + 0.5);*/
        int year = 0;
        int month = 0;
        int day = 0;

        jd -= 1948085;
//        jd +=1;
        year = (int) jd / 10631;
        year *= 30;
        int extraDays = (int) jd % 10631;
        int extraYears = getEXtraIslamicYearsByExtraDays(extraDays);
        year += extraYears;
        extraDays -= getExtraDaysByIslamicYear(extraYears);
        if (extraDays == 0) {
            year--;
            month = 12;
            day = 30;
        } else {
            month = getIslamicMonthByExtraDays(extraDays);
            extraDays -= getExtraDaysByIslamicMonth(month);
            day = extraDays;
        }
        return new IslamicDate(year, month, day);
    }

    private static int getExtraDaysByIslamicMonth(int month) {
        if (month == 1) return 0;
        if (month == 2) return 29;
        if (month == 3) return 59;
        if (month == 4) return 89;
        if (month == 5) return 119;
        if (month == 6) return 148;
        if (month == 7) return 177;
        if (month == 8) return 206;
        if (month == 9) return 236;
        if (month == 10) return 265;
        if (month == 11) return 294;
        if (month == 12) return 324;
        return 1;
    }

    private static int getIslamicMonthByExtraDays(int days) {
        int param = 1;
        if (days > 29) param = 2;
        if (days > 59) param = 3;
        if (days > 89) param = 4;
        if (days > 119) param = 5;
        if (days > 148) param = 6;
        if (days > 177) param = 7;
        if (days > 206) param = 8;
        if (days > 236) param = 9;
        if (days > 265) param = 10;
        if (days > 294) param = 11;
        if (days > 324) param = 12;
        return param;
    }

    private static int getExtraDaysByIslamicYear(int year) {
        if (year == 30) return 10631;
        if (year == 29) return 10276;
        if (year == 28) return 9922;
        if (year == 27) return 9568;
        if (year == 26) return 9213;
        if (year == 25) return 8859;
        if (year == 24) return 8504;
        if (year == 23) return 8150;
        if (year == 22) return 7796;
        if (year == 21) return 7441;
        if (year == 20) return 7087;
        if (year == 19) return 6733;
        if (year == 18) return 6378;
        if (year == 17) return 6024;
        if (year == 16) return 5669;
        if (year == 15) return 5315;
        if (year == 14) return 4961;
        if (year == 13) return 4606;
        if (year == 12) return 4252;
        if (year == 11) return 3898;
        if (year == 10) return 3543;
        if (year == 9) return 3189;
        if (year == 8) return 2835;
        if (year == 7) return 2480;
        if (year == 6) return 2126;
        if (year == 5) return 1771;
        if (year == 4) return 1417;
        if (year == 3) return 1063;
        if (year == 2) return 708;
        if (year == 1) return 354;
        if (year == 0) return 0;
        return 0;
    }

    private static int getEXtraIslamicYearsByExtraDays(long days) {
        int param = 29;
        if (days < 10631) param = 29;
        if (days < 10276) param = 28;
        if (days < 9922) param = 27;
        if (days < 9568) param = 26;
        if (days < 9213) param = 25;
        if (days < 8859) param = 24;
        if (days < 8504) param = 23;
        if (days < 8150) param = 22;
        if (days < 7796) param = 21;
        if (days < 7441) param = 20;
        if (days < 7087) param = 19;
        if (days < 6733) param = 18;
        if (days < 6378) param = 17;
        if (days < 6024) param = 16;
        if (days < 5669) param = 15;
        if (days < 5315) param = 14;
        if (days < 4961) param = 13;
        if (days < 4606) param = 12;
        if (days < 4252) param = 11;
        if (days < 3898) param = 10;
        if (days < 3543) param = 9;
        if (days < 3189) param = 8;
        if (days < 2835) param = 7;
        if (days < 2480) param = 6;
        if (days < 2126) param = 5;
        if (days < 1771) param = 4;
        if (days < 1417) param = 3;
        if (days < 1063) param = 2;
        if (days < 708) param = 1;
        if (days < 354) param = 0;
        return param;
    }

    // TODO Is it correct to return a CivilDate as a JulianDate?
    public static final CivilDate jdnToJulian(long jdn) {
        long j = jdn + 1402;
        long k = ((j - 1) / 1461);
        long l = j - 1461 * k;
        long n = ((l - 1) / 365) - (l / 1461);
        long i = l - 365 * n + 30;
        j = ((80 * i) / 2447);
        int day = (int) (i - ((2447 * j) / 80));
        i = (j / 11);
        int month = (int) (j + 2 - 12 * i);
        int year = (int) (4 * k + n + i - 4716);

        return new CivilDate(year, month, day);
    }

    public static final PersianDate jdnToPersian(long jdn) {

        long depoch = jdn - persianToJdn(475, 1, 1);
        long cycle = depoch / 1029983;
        long cyear = depoch % 1029983;
        long ycycle;
        long aux1 = 0, aux2 = 0;

        if (cyear == 1029982)
            ycycle = 2820;
        else {
            aux1 = cyear / 366;
            aux2 = cyear % 366;
            ycycle = floor(((2134 * aux1) + (2816 * aux2) + 2815) / 1028522d)
                    + aux1 + 1;
        }

        int year, month, day;
        year = (int) (ycycle + (2820 * cycle) + 474);
        if (year <= 0)
            year = year - 1;

        long yday = (jdn - persianToJdn(year, 1, 1)) + 1;
        if (yday <= 186)
            month = (int) Math.ceil(yday / 31d);
        else
            month = (int) Math.ceil((yday - 6) / 30d);

        day = (int) (jdn - persianToJdn(year, month, 1)) + 1;
        return new PersianDate(year, month, day);
    }

    public static final long julianToJdn(long lYear, long lMonth, long lDay) {

        return 367 * lYear - ((7 * (lYear + 5001 + ((lMonth - 9) / 7))) / 4)
                + ((275 * lMonth) / 9) + lDay + 1729777;

    }

    public static final CivilDate persianToCivil(PersianDate persian) {
        return jdnToCivil(persianToJdn(persian));
    }

    public static final IslamicDate persianToIslamic(PersianDate persian) {
        return jdnToIslamic(persianToJdn(persian));
    }

    public static final long persianToJdn(int year, int month, int day) {
        final long PERSIAN_EPOCH = 1948321; // The JDN of 1 Farvardin 1

        long epbase;
        if (year >= 0)
            epbase = year - 474;
        else
            epbase = year - 473;

        long epyear = 474 + (epbase % 2820);

        long mdays;
        if (month <= 7)
            mdays = (month - 1) * 31;
        else
            mdays = (month - 1) * 30 + 6;

        return day + mdays + ((epyear * 682) - 110) / 2816 + (epyear - 1) * 365
                + epbase / 2820 * 1029983 + (PERSIAN_EPOCH - 1);
    }

    public static final long persianToJdn(PersianDate persian) {
        int year = persian.getYear();
        int month = persian.getMonth();
        int day = persian.getDayOfMonth();

        final long PERSIAN_EPOCH = 1948321; // The JDN of 1 Farvardin 1

        long epbase;
        if (year >= 0)
            epbase = year - 474;
        else
            epbase = year - 473;

        long epyear = 474 + (epbase % 2820);

        long mdays;
        if (month <= 7)
            mdays = (month - 1) * 31;
        else
            mdays = (month - 1) * 30 + 6;

        return day + mdays + ((epyear * 682) - 110) / 2816 + (epyear - 1) * 365
                + epbase / 2820 * 1029983 + (PERSIAN_EPOCH - 1);
    }

    private static final double tmoonphase(long n, int nph) {

        final double RPD = (1.74532925199433E-02); // radians per degree
        // (pi/180)

        double xtra = 0;

        double k = n + nph / 4d;
        double T = k / 1236.85;
        double t2 = T * T;
        double t3 = t2 * T;
        double jd = 2415020.75933 + 29.53058868 * k - 0.0001178 * t2
                - 0.000000155 * t3 + 0.00033
                * Math.sin(RPD * (166.56 + 132.87 * T - 0.009173 * t2));

        // Sun's mean anomaly
        double sa = RPD
                * (359.2242 + 29.10535608 * k - 0.0000333 * t2 - 0.00000347 * t3);

        // Moon's mean anomaly
        double ma = RPD
                * (306.0253 + 385.81691806 * k + 0.0107306 * t2 + 0.00001236 * t3);

        // Moon's argument of latitude
        double tf = RPD
                * 2d
                * (21.2964 + 390.67050646 * k - 0.0016528 * t2 - 0.00000239 * t3);

        // should reduce to interval 0-1.0 before calculating further
        switch (nph) {
            case 0:
            case 2:
                xtra = (0.1734 - 0.000393 * T) * Math.sin(sa) + 0.0021
                        * Math.sin(sa * 2) - 0.4068 * Math.sin(ma) + 0.0161
                        * Math.sin(2 * ma) - 0.0004 * Math.sin(3 * ma) + 0.0104
                        * Math.sin(tf) - 0.0051 * Math.sin(sa + ma) - 0.0074
                        * Math.sin(sa - ma) + 0.0004 * Math.sin(tf + sa) - 0.0004
                        * Math.sin(tf - sa) - 0.0006 * Math.sin(tf + ma) + 0.001
                        * Math.sin(tf - ma) + 0.0005 * Math.sin(sa + 2 * ma);
                break;
            case 1:
            case 3:
                xtra = (0.1721 - 0.0004 * T) * Math.sin(sa) + 0.0021
                        * Math.sin(sa * 2) - 0.628 * Math.sin(ma) + 0.0089
                        * Math.sin(2 * ma) - 0.0004 * Math.sin(3 * ma) + 0.0079
                        * Math.sin(tf) - 0.0119 * Math.sin(sa + ma) - 0.0047
                        * Math.sin(sa - ma) + 0.0003 * Math.sin(tf + sa) - 0.0004
                        * Math.sin(tf - sa) - 0.0006 * Math.sin(tf + ma) + 0.0021
                        * Math.sin(tf - ma) + 0.0003 * Math.sin(sa + 2 * ma)
                        + 0.0004 * Math.sin(sa - 2 * ma) - 0.0003
                        * Math.sin(2 * sa + ma);
                if (nph == 1)
                    xtra = xtra + 0.0028 - 0.0004 * Math.cos(sa) + 0.0003
                            * Math.cos(ma);
                else
                    xtra = xtra - 0.0028 + 0.0004 * Math.cos(sa) - 0.0003
                            * Math.cos(ma);

                break;
            default:
                return 0;
        }
        // convert from Ephemeris Time (ET) to (approximate)Universal Time (UT)
        return jd + xtra - (0.41 + 1.2053 * T + 0.4992 * t2) / 1440;
    }

    private static final double visibility(long n) {

        // parameters for Makkah: for a new moon to be visible after sunset on
        // a the same day in which it started, it has to have started before
        // (SUNSET-MINAGE)-TIMZ=3 A.M. local time.
        final float TIMZ = 3f, MINAGE = 13.5f, SUNSET = 19.5f, // approximate
                TIMDIF = (SUNSET - MINAGE);

        double jd = tmoonphase(n, 0);
        long d = floor(jd);

        double tf = (jd - d);

        if (tf <= 0.5) // new moon starts in the afternoon
            return (jd + 1f);
        else { // new moon starts before noon
            tf = (tf - 0.5) * 24 + TIMZ; // local time
            if (tf > TIMDIF)
                return (jd + 1d); // age at sunset < min for visiblity
            else
                return jd;
        }

    }

}
