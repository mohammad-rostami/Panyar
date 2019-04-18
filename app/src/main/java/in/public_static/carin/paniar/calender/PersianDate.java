package in.public_static.carin.paniar.calender;


public class PersianDate extends AbstractDate {

    private static final String[] persianMonthName = {"", "فروردین",
            "اردیبهشت", "خرداد", "تیر", "مرداد", "شهریور", "مهر", "آبان",
            "آذر", "دی", "بهمن", "اسفند"};

    /**
     * Months Names in Dari, needed for special Dari Version. Provided by:
     * Mohammad Hamid Majidee
     */
    private static final String[] dariMonthName = {"", "حمل", "ثور", "جوزا",
            "سرطان", "اسد", "سنبله", "میزان", "عقرب", "قوس", "جدی", "دلو",
            "حوت"};

    private boolean isDari = false;
    private int year;
    private int month;
    private int day;

    public PersianDate(int year, int month, int day) {
        setYear(year);
        // Initialize day, so that we get no exceptions when setting month
        this.day = 1;
        setMonth(month);
        setDayOfMonth(day);
    }

    public String[] getMonthsList() {
        return isDari ? dariMonthName : persianMonthName;
    }

    public void setDari(boolean isDari) {
        this.isDari = isDari;
    }

    public PersianDate clone() {
        return new PersianDate(getYear(), getMonth(), getDayOfMonth());
    }

    public int getDayOfMonth() {
        return day;
    }

    public void setDayOfMonth(int day) {
        if (day < 1)
            throw new DayOutOfRangeException("day " + day + " is out of range!");

        if (month <= 6 && day > 31)
            throw new DayOutOfRangeException("day " + day + " is out of range!");

        if (month > 6 && month <= 12 && day > 30)
            throw new DayOutOfRangeException("day " + day + " is out of range!");

        if (isLeapYear() && month == 12 && day > 30)
            throw new DayOutOfRangeException("day " + day + " is out of range!");

        if ((!isLeapYear()) && month == 12 && day > 29)
            throw new DayOutOfRangeException("day " + day + " is out of range!");

        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month < 1 || month > 12)
            throw new MonthOutOfRangeException("month " + month
                    + " is out of range!");

        // Set the day again, so that exceptions are thrown if the
        // day is out of range
        setDayOfMonth(day);

        this.month = month;
    }

    public String getMonthName() {
        return getMonthsList()[month];
    }

    public int getWeekOfYear() {
        throw new RuntimeException("not implemented yet!");
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year == 0)
            throw new YearOutOfRangeException("Year 0 is invalid!");

        this.year = year;
    }

    public void rollDay(int amount, boolean up) {
        throw new RuntimeException("not implemented yet!");
    }

    public void rollMonth(int amount, boolean up) {
        throw new RuntimeException("not implemented yet!");
    }

    public void rollYear(int amount, boolean up) {
        throw new RuntimeException("not implemented yet!");
    }

    public String getEvent() {
        throw new RuntimeException("not implemented yet!");
    }

    public int getDayOfWeek() {
        throw new RuntimeException("not implemented yet!");
    }

    public int getDayOfYear() {
        throw new RuntimeException("not implemented yet!");
    }

    public int getWeekOfMonth() {
        throw new RuntimeException("not implemented yet!");
    }

    public boolean isLeapYear() {
        int y;
        if (year > 0)
            y = year - 474;
        else
            y = 473;
        return (((((y % 2820) + 474) + 38) * 682) % 2816) < 682;
    }

    public boolean equals(PersianDate persianDate) {
        return this.getDayOfMonth() == persianDate.getDayOfMonth()
                && this.getMonth() == persianDate.getMonth()
                && this.getYear() == persianDate.getYear();
    }
}
